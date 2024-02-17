package com.example.taggingmaterials.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("image")
data class TaggedImage(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo
    val imageUri: String,
    @ColumnInfo
    val tag1: String = "",
    @ColumnInfo
    val tag2: String = "",
    @ColumnInfo
    val tag3: String = "",
)


val testTaggedImages = listOf(
    TaggedImage(
        id = 0,
        imageUri = "https://kamiesai.com/wp-content/uploads/%E3%82%82%E3%82%82%E3%81%93_Selene%E7%89%88%E6%A8%A9%E6%9C%89_%E3%83%88%E3%83%AA%E3%83%9F%E3%83%B3%E3%82%B0%E6%B8%88%E3%81%BF-scaled.jpg",
        tag1 = "",
        tag2 = "",
        tag3 = ""
    ),
    TaggedImage(
        id = 1,
        imageUri = "https://pixiv-waengallery.com/gallery/wp-content/uploads/2019/09/76159879_p0.jpg",
        tag1 = "",
        tag2 = "",
        tag3 = ""
    ),
    TaggedImage(
        id = 2,
        imageUri = "https://pbs.twimg.com/profile_images/1218396890640744448/KuKQr5nr_400x400.jpg",
        tag1 = "",
        tag2 = "",
        tag3 = ""
    ),
    TaggedImage(
        id = 3,
        imageUri = "https://kamiesai.com/2021/wp-content/uploads/sites/3/%E5%8F%8C%E6%98%9F%E3%81%AE%E8%B8%8A%E3%82%8A%E5%AD%90%C2%A9%E4%BB%98.jpg",
        tag1 = "",
        tag2 = "",
        tag3 = ""
    ),
    TaggedImage(
        id = 4,
        imageUri = "https://i.pinimg.com/736x/f6/79/e9/f679e9521295ce6830ddfa2383d22eda.jpg",
        tag1 = "",
        tag2 = "",
        tag3 = ""
    ),
    TaggedImage(
        id = 5,
        imageUri = "https://pbs.twimg.com/media/GCubTZebkAARtj4.jpg:large",
        tag1 = "",
        tag2 = "",
        tag3 = ""
    ),
    TaggedImage(
        id = 6,
        imageUri = "https://kamiesai.com/2021/wp-content/uploads/sites/3/%E3%81%B2%E3%82%88%E3%82%8A%E3%81%A8%C2%A9%E4%BB%98-scaled.jpg",
        tag1 = "",
        tag2 = "",
        tag3 = ""
    ),
    TaggedImage(
        id = 7,
        imageUri = "https://artjeuness.jp/resource/packages/site_data/img/momoko/202312/06_%E3%81%8A%E6%B3%A8%E5%B0%84%E3%81%AE%E6%99%82%E9%96%93%E3%81%A7%E3%81%99%E3%82%88.jpg",
        tag1 = "",
        tag2 = "",
        tag3 = ""
    )
)

