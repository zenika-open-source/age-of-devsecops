package com.zenika.ageofdevsecops.infrastructure.challenge

import com.zenika.ageofdevsecops.infrastructure.challenge.checker.Flag
import com.zenika.ageofdevsecops.infrastructure.challenge.checker.FlagRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class InMemoryFlagRepository : FlagRepository {

    private val flags = Arrays.asList(
            Flag("QUIZ_BDD_DEV1_SEC0_OPS0_i6zapi62k4i9qypq9828ze3636w4wdi9q5ajp38nt997f795", 1, 0, 0),
            Flag("QUIZ_SECURITYCICD_DEV1_SEC1_OPS2_p093x471u76mfrsfpo8jbvrdfbaf05pfv4oyzl6zy2b4za9f", 1, 1, 2),
            Flag("QUIZ_DOCKER_DEV1_SEC0_OPS2_h2daqbduo3tk1o052d3ehgiy51khhg6po9plq85lh4v9i24r", 1, 0, 2),
            Flag("QUIZ_AGILITY_DEV1_SEC0_OPS0_7YcKz7zTYtb66LP6385sy5dx4auQWgS9yd9dC2LCK7H4FmX6", 1, 0, 0),
            Flag("QUIZ_RESILIENCY_DEV1_SEC0_OPS3_7YcKz7zTYtb66LP6385sy5dx4auQWgS9yd9dC2LCK7H4FmX6", 1, 0, 3),
            Flag("QUIZ_CLEANCODE_DEV1_SEC0_OPS0_g267t87uqfaj4243eaz2h74p6jct222un5t76p3s27v4va2i", 1, 0, 0),
            Flag("QUIZ_DATEFORMAT_DEV1_SEC0_OPS0_999j4ktks7w5qq7qtg3w7gi3n52767rc529i52efss43y72r", 1, 0, 0),
            Flag("WORKSHOP_LEGO4DEVOPS_DEV5_SEC2_OPS5_ZwWKKgx2LPLfjwuhxBjZ6BUPVHnCvjKq66WdBXrjrkbMVqpd", 5, 2, 5),
            Flag("WORKSHOP_LEGO4DEVOPS_DEV5_SEC2_OPS5_j9q8wq7xfi5666dtwh29k2jk9c9353c5wt4k5rr47v38m93q", 5, 2, 5),
            Flag("WORKSHOP_LOCKPICKING1_DEV0_SEC1_OPS0_gk8id9zdn40vgcnnmtwlxfsu33j7tcdzmbo5rto4nu69wj2o", 0, 1, 0),
            Flag("WORKSHOP_LOCKPICKING2_DEV0_SEC1_OPS0_0muk4ol9165dmkumb61qoshc9qgl6alw2l6w0ib7e7aqxbie", 0, 1, 0),
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

    override fun findAll(): List<Flag> {
        return flags
    }

    override fun findById(id: String): Flag? {
        return flags.find { it.id == id }
    }
}
