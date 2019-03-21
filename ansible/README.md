# Prerequisites

One must generate as many SSH keys and Elastic IPs in AWS as the number of teams.

It is required to have `ansible 2.7`, `Python` and `boto` ((`pip install boto`) installed and available in the PATH.

Players SSH keys must be in `~/.ssh` with the name `<team_name>.pem`

Two AWS profiles with sufficient permissions must be configured:
```bash
aws configure --profile deploy1
```
```bash
aws configure --profile deploy2
```

    
Pull the ugly code repository in `/tmp/ugly-system/`

Do not forget to have has `hosts` file:
``` 
[all:vars]
ansible_ssh_private_key_file=./labs-age-devsecops-slave.pem
ansible_python_interpreter=/usr/bin/python3

[team1]
{{ node1_ip }}
```

# Run the automated deployment script

Set the variables `teams`, `passwords` and `elasticIps` with correct values in `deploy.sh`, then run it to deploy all
players stacks:
```bash
./deploy.sh
```

# Manuel steps

Unfortunately, Jenkins must be configured manually:

Get the Jenkins admin password in the file `jenkins_{{team_name}}.password`.

Access the corresponding Jenkins instance (`<ip>/jenkins/`) and do the following:
- if a blank page is displayed, reboot Jenkins (`<ip>/jenkins/restart/`)
- add the SSH keys in the Jenkins credentials as a secret file with id `SSH_KEY`
- launch the preconfigured pipeline to deploy the uglycode once
