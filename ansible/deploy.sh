#!/usr/bin/env bash
set -ev

deploy_team() {
    profile=$1
    export TEAM_NAME=$2
    export PASSWORD=${3}
    export EIP=$4

    aws cloudformation deploy --template-file cloudformation.json --stack-name ${TEAM_NAME} \
     --capabilities CAPABILITY_NAMED_IAM --parameter-overrides SubnetId=subnet-3fe38259 \
      TeamName=${TEAM_NAME} TeamPassword=${PASSWORD} SSHKeyName=${TEAM_NAME} GameEngineSecurityGroup=sg-02b76f0a933ae3269 \
      BuildIPAllocationId=${EIP} --profile ${profile}

    sleep 30

    export NODE1_IP="$(aws ec2 describe-instances  \
      --filter Name=tag:team,Values=${TEAM_NAME} Name=instance-state-name,Values=running Name=tag:usage,Values=node1 \
       --query='Reservations[].Instances[].{ Instance: Tags[?Key==`Name`]|[0].Value, PrivateIP: PrivateIpAddress}' --profile ${profile} \
       | jq '.[0].PrivateIP' | sed 's/\"//g')"

   export BUILD_IP="$(aws ec2 describe-instances  \
      --filter Name=tag:team,Values=${TEAM_NAME} Name=instance-state-name,Values=running Name=tag:usage,Values=build \
       --query='Reservations[].Instances[].{ Instance: Tags[?Key==`Name`]|[0].Value, PublicIP: PublicIpAddress}' --profile ${profile} \
       | jq '.[0].PublicIP' | sed 's/\"//g')"

    ssh-add ~/.ssh/${TEAM_NAME}.pem

    ansible-playbook -i inventory/ec2.py -e 'ansible_python_interpreter=/usr/bin/python3' \
     --limit "tag_usage_build:&tag_team_${TEAM_NAME}" buildInstance.yml

    ansible-playbook -i inventory/ec2.py -e 'ansible_python_interpreter=/usr/bin/python3' \
     --limit "tag_usage_loadbalancer:&tag_team_${TEAM_NAME}" loadbalancer.yml

    ssh-keygen -f "/home/zenikalabs/.ssh/known_hosts" -R ${BUILD_IP}
    ssh-keygen -F ${BUILD_IP} || ssh-keyscan ${BUILD_IP} >> ~/.ssh/known_hosts

    sleep 15

    # init GIT
    git_dir=/tmp/ugly-system-${TEAM_NAME}/
    cp -R /tmp/ugly-system/ ${git_dir}
    cd ${git_dir}
    sed -i "s/{{ node1_ip }}/${NODE1_IP}/g" ./deploy/hosts
    git add .
    git commit --amend --no-edit
    git remote add ${TEAM_NAME} ubuntu@${BUILD_IP}:ugly-system.git
    git push ${TEAM_NAME} master:master -f

    rm -rf ${git_dir}

    cd -
    ssh-add -D
}

teams=( akinez aneitov astriturn azuno carebos dakonus dragano estea kaiphus kinziri luuria niborus obade ogophus ophore piwei pucara strooter tesade thunides tivilles trioteru votis xenvion zatruna )

passwords=( QE5tR44sT8nf9p8Q 54GM2R35dHSihtr6 WkA4643mryRfJ3R9 gZtN6fv8P36dJ82Q ii8456Zir6DNkS3S p67nXj5qe8NHG77E PP85a4q5Cne4CH8x fTQ72u6F6epuUU54 Vp7439TtEnh25tVE 89PQ3qQu3L9i8Luf 2Q9j8UC9dhm2SSx4 5rV35Y3fYkG45rPa p97em94VU37GrLaJ 59w4Z7rXSfVm4T8e JA9M7382yqnYt5Nw 5eavYd6C93L24AQd 5BP9SSugyza443A3 Bt7GeE84i6GWkz96 k45WRkrb7Bp2D23Q gx7LAvXH58E76e9i 2E8DKm8sNnfN25k6 N646PbxZST2n83wy Qv4Z3e3XtT8d8k4F 5kV7d99AZb2it2BZ Sn7t3Sm4B4v56NGm )

elasticIps=( eipalloc-012fce58295bab534 eipalloc-04adaecac981cd16c eipalloc-07656afa3b56bf710 eipalloc-06003bf412062e9cb eipalloc-04f1ce363a889d8b9 eipalloc-0aa53ec5f51ba506f eipalloc-026770a72b6790f3b eipalloc-06baf84f40d3bf7fc eipalloc-04023ce8ce53e98a4 eipalloc-01cb68eabf7683978 eipalloc-03affd4908cbb86ec eipalloc-077d4d092834daf78 eipalloc-035df517b7b0b0d1d eipalloc-05dbf6342fd54bc3e eipalloc-094b4583f808e0a74 eipalloc-0ac85dc1ca8a9d73f eipalloc-090de5ff87233123a eipalloc-0365d8c9114274f64 eipalloc-0432507c15e2ccca6 eipalloc-0f48858eb14a51c0c eipalloc-00309ddb0bf1a74a9 eipalloc-0977f89ea5765540b eipalloc-085e1f98b43763453 eipalloc-0944b84a3df4a90eb eipalloc-06dfebbe79f7f2df0 eipalloc-0de7a678f61b87ddd eipalloc-0533a025efab5e9bb eipalloc-0a5317b93443e0678 eipalloc-0e72ad9515e960ad3 eipalloc-010679e81a0a38231 )

for i in $(seq 0 1 14)
do :
    deploy_team deploy1 ${teams[$i]} ${passwords[$i]} ${elasticIps[$i]}
done


for i in $(seq 15 1 24)
do :
     deploy_team deploy2 ${teams[$i]} ${passwords[$i]} ${elasticIps[$i]}
done


