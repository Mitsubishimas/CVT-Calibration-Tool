package com.cvt.admin.data.models

import com.google.gson.annotations.SerializedName

data class ForumCategory(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("description")
    val description: String,
    
    @SerializedName("order")
    val order: Int,
    
    @SerializedName("topics_count")
    val topicsCount: Int = 0,
    
    @SerializedName("posts_count")
    val postsCount: Int = 0
)

data class ForumTopic(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("category_id")
    val categoryId: Int,
    
    @SerializedName("category_name")
    val categoryName: String,
    
    @SerializedName("title")
    val title: String,
    
    @SerializedName("author")
    val author: String,
    
    @SerializedName("author_id")
    val authorId: Int,
    
    @SerializedName("posts_count")
    val postsCount: Int,
    
    @SerializedName("views")
    val views: Int,
    
    @SerializedName("is_pinned")
    val isPinned: Boolean = false,
    
    @SerializedName("is_locked")
    val isLocked: Boolean = false,
    
    @SerializedName("last_post_date")
    val lastPostDate: String,
    
    @SerializedName("created_at")
    val createdAt: String
)

data class ForumPost(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("topic_id")
    val topicId: Int,
    
    @SerializedName("topic_title")
    val topicTitle: String,
    
    @SerializedName("author")
    val author: String,
    
    @SerializedName("author_id")
    val authorId: Int,
    
    @SerializedName("content")
    val content: String,
    
    @SerializedName("created_at")
    val createdAt: String
)

// 2. Модель Tariff
data class Tariff(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("price")
    val price: Double,
    
    @SerializedName("duration_days")
    val durationDays: Int,
    
    @SerializedName("description")
    val description: String? = null,
    
    @SerializedName("is_active")
    val isActive: Boolean = true
)

// 3. Модель Mail
data class EmailMessage(
    @SerializedName("id")
    val id: String,
    
    @SerializedName("from")
    val from: String,
    
    @SerializedName("to")
    val to: String? = null,
    
    @SerializedName("subject")
    val subject: String,
    
    @SerializedName("body")
    val body: String,
    
    @SerializedName("date")
    val date: String,
    
    @SerializedName("is_read")
    val isRead: Boolean = false,
    
    @SerializedName("has_attachment")
    val hasAttachment: Boolean = false
)

data class SendEmailRequest(
    val to: String,
    val subject: String,
    val body: String,
    val replyToId: String? = null
)

// 4. Модель Software
data class Software(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("version")
    val version: String,
    
    @SerializedName("description")
    val description: String,
    
    @SerializedName("file_size")
    val fileSize: Long,
    
    @SerializedName("file_url")
    val fileUrl: String,
    
    @SerializedName("icon_url")
    val iconUrl: String? = null,
    
    @SerializedName("is_free")
    val isFree: Boolean = true,
    
    @SerializedName("price")
    val price: Double = 0.0,
    
    @SerializedName("downloads_count")
    val downloadsCount: Int = 0,
    
    @SerializedName("created_at")
    val createdAt: String
)

// 5. Общие модели
data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val message: String? = null,
    val error: String? = null
)

data class PaymentTexts(
    @SerializedName("success_title")
    val successTitle: String,
    
    @SerializedName("amount")
    val amount: Double,
    
    @SerializedName("payment_phone")
    val paymentPhone: String,
    
    @SerializedName("payment_comment")
    val paymentComment: String,
    
    @SerializedName("after_payment_text")
    val afterPaymentText: String,
    
    @SerializedName("button_text")
    val buttonText: String
)
