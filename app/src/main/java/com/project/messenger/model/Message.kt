package com.project.messenger.model

class Message(
    val id: String = "",
    val currentUser: String = "",
    val partner: String = "",
    val message: String = "",
    val doubleTimeSource: String = ""
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Message

        if (id != other.id) return false
        if (currentUser != other.currentUser) return false
        if (partner != other.partner) return false
        if (message != other.message) return false
        if (doubleTimeSource != other.doubleTimeSource) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + currentUser.hashCode()
        result = 31 * result + partner.hashCode()
        result = 31 * result + message.hashCode()
        result = 31 * result + doubleTimeSource.hashCode()
        return result
    }

    override fun toString(): String {
        return "Message(id='$id', currentUser='$currentUser', partner='$partner', message='$message', doubleTimeSource='$doubleTimeSource')"
    }
}