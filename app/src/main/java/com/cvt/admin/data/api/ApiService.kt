package com.cvt.admin.data.api

import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    
    @POST("admin_login.php")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
    
    @GET("admin_stats.php")
    suspend fun getStats(@Header("Authorization") token: String): Response<StatsResponse>
    
    @GET("admin_orders.php")
    suspend fun getOrders(@Header("Authorization") token: String, @Query("type") type: String): Response<List<Order>>
    
    @POST("admin_mark_paid.php")
    suspend fun markAsPaid(@Header("Authorization") token: String, @Body request: MarkPaidRequest): Response<BaseResponse>
    
    @GET("admin_chat.php")
    suspend fun getChatUsers(@Header("Authorization") token: String, @Query("action") action: String = "get_users"): Response<List<ChatUser>>
    
    @GET("admin_chat.php")
    suspend fun getMessages(@Header("Authorization") token: String, @Query("action") action: String = "get_messages", @Query("user_id") userId: Int): Response<List<ChatMessage>>
    
    @POST("admin_chat.php")
    suspend fun sendMessage(@Header("Authorization") token: String, @Query("action") action: String = "send", @Body request: SendMessageRequest): Response<BaseResponse>
    
    @GET("admin_keys.php")
    suspend fun getKeys(@Header("Authorization") token: String): Response<List<ActivationKey>>
    
    @POST("admin_keys.php")
    suspend fun generateKey(@Header("Authorization") token: String, @Body request: GenerateKeyRequest): Response<GenerateKeyResponse>
    
    @GET("admin_users.php")
    suspend fun getUsers(@Header("Authorization") token: String): Response<List<User>>
    
    @POST("admin_users.php")
    suspend fun toggleBlockUser(@Header("Authorization") token: String, @Query("action") action: String = "toggle_block", @Query("id") userId: Int): Response<BaseResponse>
}

// Data classes
data class LoginRequest(val password: String)
data class LoginResponse(val token: String, val expiresIn: Long)
data class StatsResponse(val users: UserStats, val subscriptions: SubscriptionStats, val keys: KeyStats, val revenue: Double)
data class UserStats(val total: Int, val newToday: Int)
data class SubscriptionStats(val active: Int)
data class KeyStats(val generated: Int, val activated: Int)

data class Order(
    val id: Int,
    val orderNumber: String,
    val email: String,
    val phone: String,
    val productName: String,
    val amount: Double,
    val status: String,
    val createdAt: String
)

data class ChatUser(
    val id: Int,
    val name: String,
    val email: String,
    val unreadCount: Int,
    val lastMessage: String?,
    val lastMessageTime: Long?
)

data class ChatMessage(
    val id: Int,
    val userId: Int,
    val userName: String,
    val message: String,
    val timestamp: Long,
    val isFromAdmin: Boolean,
    val isRead: Boolean
)

data class ActivationKey(
    val id: Int,
    val key: String,
    val phone: String,
    val email: String,
    val tariff: String,
    val status: String,
    val createdAt: String
)

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val isBlocked: Boolean,
    val subscriptionUntil: String?
)

data class MarkPaidRequest(val order_id: Int, val type: String)
data class SendMessageRequest(val user_id: Int, val message: String)
data class GenerateKeyRequest(val phone: String, val computer_id: String, val email: String, val tariff: String)
data class GenerateKeyResponse(val success: Boolean, val key: String?, val message: String?)
data class BaseResponse(val success: Boolean, val message: String)
