package com.sewar.sary.data.models

import android.net.Uri
import android.os.Parcelable
import androidx.core.net.toUri
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Catalog(
    val id: Int,
    val title: String,
    val groups: List<Group>,
    val dataType: DataType,
    val showTitle: Boolean,
    val uiType: UiType,
    val rowCount: Int,
) : Parcelable {
    enum class DataType { Smart, Group, Banner }

    enum class UiType { Grid, Linear, Slider }

    @Parcelize
    data class Group(
        val id: Int,
        val name: String,
        val image: Uri?,
        val deepLink: Uri?,
        val emptyContentImage: Uri?,
        val emptyContentMessage: String?,
        val hasData: Boolean,
        val showUnavailableItems: Boolean,
        val showInBrochureLink: Boolean,
        val dataType: DataType,
        val rowCount: Int,
    ) : Parcelable
}

private fun dataTypeFromString(string: String): Catalog.DataType? {
    return when (string) {
        "smart" -> Catalog.DataType.Smart
        "group" -> Catalog.DataType.Group
        "banner" -> Catalog.DataType.Banner
        else -> null
    }
}

private fun uiTypeFromString(string: String): Catalog.UiType? {
    return when (string) {
        "grid" -> Catalog.UiType.Grid
        "linear" -> Catalog.UiType.Linear
        "slider" -> Catalog.UiType.Slider
        else -> null
    }
}

data class CatalogContainer(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("data") val groups: List<GroupContainer>,
    @SerializedName("data_type") val dataTypeString: String,
    @SerializedName("show_title") val showTitle: Boolean,
    @SerializedName("ui_type") val uiTypeString: String,
    @SerializedName("row_count") val rowCount: Int,
) {
    fun toCatalog(): Catalog? {
        val dataType = dataTypeFromString(dataTypeString) ?: return null
        val uiType = uiTypeFromString(uiTypeString) ?: return null

        return Catalog(
            id,
            title,
            groups.map { it.toGroup(dataType, rowCount) },
            dataType,
            showTitle,
            uiType,
            rowCount,
        )
    }

    data class GroupContainer(
        @SerializedName("group_id") val id: Int,
        @SerializedName("name") val name: String?,
        @SerializedName("image") val imageString: String?,
        @SerializedName("deep_link") val deepLinkString: String?,
        @SerializedName("empty_content_image") val emptyContentImageString: String?,
        @SerializedName("empty_content_message") val emptyContentMessage: String?,
        @SerializedName("has_data") val hasData: Boolean,
        @SerializedName("show_unavailable_items") val showUnavailableItems: Boolean,
        @SerializedName("show_in_brochure_link") val showInBrochureLink: Boolean,
    ) {
        fun toGroup(dataType: Catalog.DataType, rowCount: Int): Catalog.Group {
            return Catalog.Group(
                id,
                name.orEmpty(),
                imageString?.toUri(),
                deepLinkString?.toUri(),
                emptyContentImageString?.toUri(),
                emptyContentMessage,
                hasData,
                showUnavailableItems,
                showInBrochureLink,
                dataType,
                rowCount,
            )
        }
    }
}

data class CatalogsResponse(
    @SerializedName("result") val results: List<CatalogContainer>,
//    @SerializedName("other") val other: String,
    @SerializedName("message") val message: String,
    @SerializedName("status") val status: Boolean,
) {
    fun toCatalogs(): List<Catalog> {
        return results.mapNotNull(CatalogContainer::toCatalog)
    }
}
