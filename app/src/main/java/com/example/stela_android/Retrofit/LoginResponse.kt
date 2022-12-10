package com.example.stela_android.Retrofit

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class User(

	@field:SerializedName("unit_kerja")
	val unitKerja: Any? = null,

	@field:SerializedName("nama_lengkap")
	val namaLengkap: String? = null,

	@field:SerializedName("telepon")
	val telepon: Any? = null,

	@field:SerializedName("hp")
	val hp: Any? = null,

	@field:SerializedName("gedung")
	val gedung: Any? = null,

	@field:SerializedName("bagian")
	val bagian: Any? = null,

	@field:SerializedName("ruangan")
	val ruangan: Any? = null,

	@field:SerializedName("peran")
	val peran: List<String?>? = null,

	@field:SerializedName("lantai")
	val lantai: Any? = null,

	@field:SerializedName("kd_departemen")
	val kdDepartemen: Any? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Data(

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("token")
	val token: String? = null
)
