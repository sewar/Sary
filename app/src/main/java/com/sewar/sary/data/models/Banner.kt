package com.sewar.sary.data.models

import android.net.Uri
import android.os.Parcelable
import androidx.core.net.toUri
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

@Parcelize
data class Banner(
    val id: Int,
    val title: String,
    val description: String,
    val buttonText: String,
    val expiryStatus: Boolean,
    val createdDate: LocalDate,
    val startDate: LocalDate,
    val expiryDate: LocalDate,
    val image: Uri,
    val priority: Int,
    val photo: Uri,
    val link: Uri,
    val level: String,
    val isAvailable: Boolean,
    val branch: Int,
) : Parcelable

data class BannerContainer(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("button_text") val buttonText: String,
    @SerializedName("expiry_status") val expiryStatus: Boolean,
    @SerializedName("created_at") val createdDateString: String,
    @SerializedName("start_date") val startDateString: String,
    @SerializedName("expiry_date") val expiryDateString: String,
    @SerializedName("image") val imageString: String,
    @SerializedName("priority") val priority: Int,
    @SerializedName("photo") val photoString: String,
    @SerializedName("link") val linkString: String,
    @SerializedName("level") val level: String,
    @SerializedName("is_available") val isAvailable: Boolean,
    @SerializedName("branch") val branch: Int,
) {
    fun toBanner(): Banner {
        val createdDate = LocalDate.parse(createdDateString, DateTimeFormatter.ISO_DATE)
        val startDate =
            LocalDate.parse(startDateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        val expiryDate =
            LocalDate.parse(expiryDateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"))


        return Banner(
            id,
            title,
            description,
            buttonText,
            expiryStatus,
            createdDate,
            startDate,
            expiryDate,
            imageString.toUri(),
            priority,
            photoString.toUri(),
            linkString.toUri(),
            level,
            isAvailable,
            branch,
        )
    }
}

data class BannersResponse(
    @SerializedName("result") val results: List<BannerContainer>,
    @SerializedName("status") val status: Boolean,
) {
    fun toBanners(): List<Banner> {
        return results.map(BannerContainer::toBanner)
    }
}
