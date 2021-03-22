package com.reelme.app.model3

import java.util.*

data  class UserDetails (
        var phoneNumber: String = "",
        var referalCode: String? ="",
        var skipReferalCode: Boolean? = false,
        var emailId: String?="",
        var firstName: String?="",
        var secondName: String?="",
        var dob: Date? = Date(),
        var username: String? ="",
        var skipUsername: Boolean? = false,
        var profilePic: String? ="",
        var bio: String? ="",
        var gender: String?="",
        var skipGender: Boolean? = false,
        var relationshipStatus: String?="",
        var skipRelationshipStatus: Boolean? = false,
        var children: String?="",
        var skipChildren: String?="",
        var occupation: String?="",
        var skipOccupation: Boolean? = false,
        var religiousBeliefs: String?="",
        var skipReligiousBeliefs: Boolean? = false,
        var hobbiesAndInterest: String?="",
        var skipHobbiesAndInterest: Boolean? = false

)