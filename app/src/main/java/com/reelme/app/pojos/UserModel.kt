package com.reelme.app.pojos

import java.util.*

data class UserModel(
        var phoneNumber: String = "",
        var referalCode: String? ="",
        var skipReferalCode: Boolean? = false,
        var emailId: String?="",
        var firstName: String?="",
        var secondName: String?="",
        var dob: String?="",
        var username: String? ="",
        var skipUsername: Boolean? = false,
        var profilePic: String? ="",
        var bio: String? ="",
        var gender: String?="",
        var skipGender: Boolean? = false,
        var relationshipStatus: String?="",
        var skipRelationshipStatus: Boolean? = false,
        var children: String?="",
        var skipChildren: Boolean?=false,
        var occupation: String?="",
        var skipOccupation: Boolean? = false,
        var religiousBeliefs: String?="",
        var skipReligiousBeliefs: Boolean? = false,
        var hobbiesAndInterest: String?="",
        var skipHobbiesAndInterest: Boolean? = false
)