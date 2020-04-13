package com.jovines.lbs_server.entity

import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id

/**
 * (User)实体类
 *
 * @author makejava
 * @since 2020-04-11 21:24:09
 */
@Entity
class User(
        @Id
        var phone: Long = 0,
        var nickname: String = "",
        var password: String = "")
    : Serializable {

    private val serialVersionUID = -79397126901599023L
}