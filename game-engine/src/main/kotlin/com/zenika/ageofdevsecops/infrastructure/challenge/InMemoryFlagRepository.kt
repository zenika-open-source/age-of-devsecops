package com.zenika.ageofdevsecops.infrastructure.challenge

import com.zenika.ageofdevsecops.infrastructure.challenge.checker.Flag
import com.zenika.ageofdevsecops.infrastructure.challenge.checker.FlagRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class InMemoryFlagRepository : FlagRepository {
    override fun findAll(): List<Flag> {
        return Arrays.asList(
                Flag("QUIZ_BEINGAGILEBEFORETHECODE_DEV1_SEC0_OPS0_csv37z57563j9x7ivan4u2v2g44396y46azs5qn8235yihxq", 1, 0, 0), 
                Flag("QUIZ_CLOUDWARS_DEV0_SEC1_OPS0_ki435726wh699mg5m3xu2mn6b7u9be2iq8n77j6xaibr3522", 0, 1, 0), 
                Flag("QUIZ_PROMETHEUSGRAFANA_DEV1_SEC0_OPS1_t8fw8p4mv5rvg2t832i328762kkjqj3n7hw2395bx8x67e5z", 1, 0, 1), 
                Flag("QUIZ_BUILDATCLOUDSPEED_DEV0_SEC0_OPS1_d642fgb67mnuzgh33558ya7xs592t6bv946s262cptn87j2v", 0, 0, 1), 
                Flag("QUIZ_CONVERSATIONALAI_DEV1_SEC0_OPS0_h7s582j96p873h8c77mu2biy8sj62gkj8k8678y4yycc4k6f", 1, 0, 0),
                Flag("QUIZ_ITALIANMYAXA_DEV1_SEC0_OPS1_97m897j2c9773nrmgxnf8573nn9fzpfd42774p4i23sxmw7s", 1, 0, 1), 
                Flag("QUIZ_FALLINGDOWNHOLES_DEV1_SEC0_OPS0_72959m3nuek7d7p7iruxfx5857y5abbf9659d4rub6552ke9", 1, 0, 0), 
                Flag("QUIZ_PROGRAMMINGTHECLOUD_DEV1_SEC0_OPS1_63n27nv3243r3xeyiq2445656knsbbg779y4vbebk47pg9k5", 1, 0, 1), 
                Flag("QUIZ_SHIFTINGSECURITYLEFT_DEV0_SEC1_OPS0_28ej92d2xc5s7b45vbcp49339xapth6d57f8z3r6h42km39m", 0, 1, 0), 
                Flag("QUIZ_BDD_DEV1_SEC0_OPS0_i6zapi62k4i9qypq9828ze3636w4wdi9q5ajp38nt997f795", 1, 0, 0), 
                Flag("QUIZ_SECURITYCICD_DEV1_SEC1_OPS2_p093x471u76mfrsfpo8jbvrdfbaf05pfv4oyzl6zy2b4za9f", 1, 1, 2),
                Flag("QUIZ_DOCKER_DEV1_SEC0_OPS2_h2daqbduo3tk1o052d3ehgiy51khhg6po9plq85lh4v9i24r", 1, 0, 2),
                Flag("QUIZ_AGILITY_DEV1_SEC0_OPS0_7YcKz7zTYtb66LP6385sy5dx4auQWgS9yd9dC2LCK7H4FmX6", 1, 0, 0),
                Flag("QUIZ_RESILIENCY_DEV1_SEC0_OPS3_7YcKz7zTYtb66LP6385sy5dx4auQWgS9yd9dC2LCK7H4FmX6", 1, 0, 3),
                Flag("QUIZ_BUILDINGANAGILEDATASCIENCE_DEV1_SEC0_OPS0_2h69x439hvx577kn4r2tk768wt3mgauc6em2u85t4q956f7s", 1, 0, 0), 
                Flag("QUIZ_UITESTAUTOMATION_DEV1_SEC0_OPS0_kz7587pk975qhwdw78es99q6i9f879nf56u356v3tinp6n3t", 1, 0, 0), 
                Flag("QUIZ_DATAPROCESSING_DEV1_SEC0_OPS0_d5g3m6mahq5rdtqy9376n297gg6mh6v7bt83226b7r9c6x94", 1, 0, 0), 
                Flag("QUIZ_GRAPHQLANDNODJS_DEV1_SEC0_OPS0_hevabr599j759zqy36m6d8sp57cfnkf93i998q922e6d9v95", 1, 0, 0), 
                Flag("QUIZ_INSURANCECLAIMTRIAGE_DEV1_SEC0_OPS0_3nk4ab5j24becj258mjt4565m2u228t7z97qmn9r5y5f8k2p", 1, 0, 0), 
                Flag("QUIZ_CONTINUOUSTESTINGINTHECLOUD_DEV0_SEC0_OPS1_5sg298et66w8de29qdem4cp5j5598345cs75skm52zr8g6bb", 0, 0, 1), 
                Flag("QUIZ_FROMSTONEAGETOMODERNITY_DEV1_SEC0_OPS0_upfc5p2u48br6v5uj9j62nz3498w9qnzf5z49662mp553gu4", 1, 0, 0), 
                Flag("QUIZ_JAVASCRIPTDEVELOPER_DEV1_SEC0_OPS0_u39dsx9vf6929q3m26si2yqb8c253z9gdf7wv9nn7m92889y", 1, 0, 0), 
                Flag("QUIZ_ZEROTOUCHPIAASTOPRODUCTION_DEV0_SEC0_OPS1_nd5i7an28ryru6bz3w6j737v89694k6uv67j5afa794ah7c3", 0, 0, 1),
                Flag("QUIZ_CLEANCODE_DEV1_SEC0_OPS0_g267t87uqfaj4243eaz2h74p6jct222un5t76p3s27v4va2i", 1, 0, 0),
                Flag("QUIZ_DATEFORMAT_DEV1_SEC0_OPS0_999j4ktks7w5qq7qtg3w7gi3n52767rc529i52efss43y72r", 1, 0, 0),
                Flag("WORKSHOP_LEGO4DEVOPS_DEV5_SEC2_OPS5_ZwWKKgx2LPLfjwuhxBjZ6BUPVHnCvjKq66WdBXrjrkbMVqpd", 5, 2, 5),
                Flag("WORKSHOP_LEGO4DEVOPS_DEV5_SEC2_OPS5_j9q8wq7xfi5666dtwh29k2jk9c9353c5wt4k5rr47v38m93q", 5, 2, 5),
                Flag("WORKSHOP_ESCAPEGAME1_DEV1_SEC1_OPS1_2wnzibs7dhjp9g4n5repex3yj8ao6x6ez037h1408tg8fa1a", 1, 1, 1),
                Flag("WORKSHOP_ESCAPEGAME2_DEV3_SEC3_OPS3_gpo1oxeku1g8mvw2txpez16xzvo17h5dwz5bn91ngn18on08", 3, 3, 3),
                Flag("WORKSHOP_CODEBATTLE_DEV3_SEC0_OPS0_hmr5fip92l4m1rcf4vjwx7a71hnb78luk3p5rl4jceb3wd96", 3, 0, 0),
                Flag("WORKSHOP_CTFSECU1_DEV1_SEC1_OPS1_yhax3s247gi0wfby2vbauifv48bxygeylszyfk4q1lcedy70", 1, 1, 1),
                Flag("WORKSHOP_CTFSECU2_DEV2_SEC3_OPS2_s2glpb2ji74ipz9f1cx36gvq7gmjci1ua765pyexbomqulbl", 2, 3, 2),
                Flag("WORKSHOP_LOCKPICKING1_DEV0_SEC1_OPS0_gk8id9zdn40vgcnnmtwlxfsu33j7tcdzmbo5rto4nu69wj2o", 0, 1, 0),
                Flag("WORKSHOP_LOCKPICKING2_DEV0_SEC1_OPS0_0muk4ol9165dmkumb61qoshc9qgl6alw2l6w0ib7e7aqxbie", 0, 1, 0),
                Flag("WORKSHOP_KAHOOT_DEV2_SEC2_OPS2_lvll7y88nbcxv73h1owqf6uzd90sigkthv4f0itdjv0589sz", 2, 2, 2),
                Flag("WORKSHOP_OWASP_DEV0_SEC3_OPS0_73cdm877zjp3428g4xvu4cybt2kfea5797u42ms49b8k8y87", 0, 3, 0),
                Flag("WORKSHOP_DRMAD_DEV0_SEC2_OPS0_im5r86b85j8q9ct3q2238ix5r969744u3kmkk6g4tdfxh4d8", 0, 2, 0), 
                Flag("WORKSHOP_EVENTSTORMING_DEV1_SEC1_OPS1_fdzdgq96py282i6p4nj6r3h8v695c444v3c97s5h9mhg5f88", 1, 1, 1), 
                Flag("SECRET_REDTEAMING_DEV0_SEC5_OPS0_kG4Q77PymVTe7Mm6NtNRkF3HuptQz93Ur6842bEw8Wz9T24i", 0, 5, 0),
                Flag("SECRET_COMMIT_DEV0_SEC1_OPS0_xtce92s3u3njh54746uni43fw7t352he83z82sa4prj6a953", 0, 1, 0),
                Flag("SECRET_0_DEV5_SEC3-OPS4_2HcQaXfsHrFw3P2LD4JIY0nbd71Rsj07LX34jc6J9345mcAs", Int.MIN_VALUE, Int.MIN_VALUE, Int.MIN_VALUE),
                Flag("SECRET_1_DEV0_SEC1_OPS0_Y299rW5yJTQf8etEyt3y7C9a2PzGKpXSdQ62t4i7R8d7fPD7", 0, 1, 0), 
                Flag("SECRET_2_DEV0_SEC1_OPS0_5iZyvE5mTYcSZD7h7NQ7Nb7buzAY7akS62867Lhe85Xfg6A2", 0, 1, 0), 
                Flag("SECRET_3_DEV0_SEC1_OPS0_F7F56n258We2W8itqiVaeYfW8P6J3iQ67Mjt2hS4fasMBS8K", 0, 1, 0), 
                Flag("SECRET_4_DEV0_SEC0_OPS1_TTbXeAqaz8W28E585aavf8tpPRaT56DCCv3TU47vTbD852k6", 0, 0, 1), 
                Flag("SECRET_5_DEV0_SEC0_OPS1_q5v9mGZ3WNkU329k9p675D455nn47vUGXFrnMLPwUbzMM9yi", 0, 0, 1), 
                Flag("SECRET_6_DEV0_SEC2_OPS0_3y6krYKmz4ePVJtWA22Y423382x6S4BfCzBwuNdPd9Y8bsU8", 0, 2, 0), 
                Flag("SECRET_7_DEV0_SEC2_OPS0_ELth5qWg6MJnZ227k5c2L67rXD6pCTUMk3M34Rym2t4b3Exz", 0, 2, 0), 
                Flag("SECRET_8_DEV0_SEC2_OPS0_Jhd7vzV892DG7QAnefHc59Lr5L999wVDcYXar3Gvca9527UX", 0, 2, 0), 
                Flag("SECRET_9_DEV0_SEC0_OPS2_59S74TSc9P9dAP46Yc22FwG5ViDa8a6gW9aE8uAdpkd8WheW", 0, 0, 2), 
                Flag("SECRET_10_DEV0_SEC0_OPS2_8Z38ia678SBB37t8EdD5tXHKzSWjbM26RWbr4qbDA8gaty32", 0, 0, 2)
        )
    }
}
