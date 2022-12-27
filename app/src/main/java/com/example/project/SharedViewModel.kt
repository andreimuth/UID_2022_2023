package com.example.project

import androidx.lifecycle.ViewModel
import com.example.project.models.Comment
import com.example.project.models.Post

class SharedViewModel : ViewModel() {
    // TODO : Add mocked data for each part of the app

    val feedPosts: MutableList<Post> = (0..20).map {
        Post(it,
            0,
            "Username $it",
            "Date $it",
            " $it Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam in turpis tincidunt, varius odio quis, tincidunt mi. Sed vitae semper quam. Praesent feugiat, odio consectetur elementum facilisis, metus risus tincidunt nibh, et tempus lorem tellus quis quam. Integer vitae ante eget eros rutrum commodo. Etiam dignissim cursus bibendum. Etiam consequat dui vel purus mattis, non posuere justo dictum. Nunc ac sapien et ante finibus scelerisque eget in augue. Vivamus mi purus, condimentum sit amet accumsan vitae, venenatis vel felis. Nam tincidunt erat sed urna auctor, et porttitor quam fermentum. Morbi ultricies urna ut nulla molestie efficitur. Cras vitae diam nec ante laoreet scelerisque non quis quam. Etiam mollis felis at nibh aliquet, et feugiat metus facilisis. Phasellus quis ligula elementum, aliquam neque sit amet, tempor tortor. Proin et tellus quis odio iaculis rhoncus vitae sit amet diam.",
            (0..5).map { commentNr ->
                Comment(
                    commentNr,
                    0,
                    "user $commentNr",
                    "date $commentNr",
                    "comment nr $commentNr"
                )
            }.toMutableList())
    }.toMutableList()


    fun addComment(postId: Int, comment: Comment) {
        feedPosts[postId].comments.add(comment)
    }

}