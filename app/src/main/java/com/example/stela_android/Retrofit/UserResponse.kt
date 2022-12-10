package com.example.stela_android.Retrofit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserResponse {

    @SerializedName("user")
    @Expose
    var data: User? = null

    class User{
        @SerializedName("nama_lengkap")
        @Expose
        var nama_lengkap: String? = null

        @SerializedName("username")
        @Expose
        var username: String? = null

        @SerializedName("email")
        @Expose
        var email: String? = null

        @SerializedName("token")
        @Expose
        var token: String? = null
    }
}

//"user": {
//    "id": "2",
//    "nama_lengkap": "user1",
//    "username": "user1",
//    "email": "user1@gmail.com",
//    "status": "1",
//    "kd_departemen": null,
//    "bagian": null,
//    "gedung": null,
//    "unit_kerja": null,
//    "ruangan": null,
//    "lantai": null,
//    "telepon": null,
//    "hp": null,
//    "peran": [
//    "verificator",
//    "helpdesk"
//    ]
//},
//"token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6IjIiLCJuYW1hX2xlbmdrYXAiOiJ1c2VyMSIsInVzZXJuYW1lIjoidXNlcjEiLCJlbWFpbCI6InVzZXIxQGdtYWlsLmNvbSIsInN0YXR1cyI6IjEiLCJrZF9kZXBhcnRlbWVuIjpudWxsLCJiYWdpYW4iOm51bGwsImdlZHVuZyI6bnVsbCwidW5pdF9rZXJqYSI6bnVsbCwicnVhbmdhbiI6bnVsbCwibGFudGFpIjpudWxsLCJ0ZWxlcG9uIjpudWxsLCJocCI6bnVsbCwicGVyYW4iOlsidmVyaWZpY2F0b3IiLCJoZWxwZGVzayJdLCJleHAiOjE2NzA2ODMxMDZ9.119OnTL-41YZ39TcNmgQHwXdbiAI21BnJ8sGEBoSY_g"
//}
//}