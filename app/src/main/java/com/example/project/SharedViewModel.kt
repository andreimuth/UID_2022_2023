package com.example.project

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.project.models.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedViewModel : ViewModel() {
    // TODO : Add mocked data for each part of the app

    var feedPosts: List<Post> = (0..20).map {
        Post(
            it,
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
            }.toMutableList(),
            Flag.NONE,
            PostType.POST
        )
    }.toMutableList()

    var groups: List<Group> = (0..20).map { groupNr ->
        Group(
            groupNr,
            "Group $groupNr",
            "This is group number $groupNr",
            mutableListOf(),
            mutableListOf()
        )
    }.toMutableList()

    var loggedInUser: User = User(-1, -1, "a", "a", UserType.MODERATOR)

    private val postsToApproveStateFlow: MutableStateFlow<List<Post>> = MutableStateFlow(emptyList())
    val postsToApproveFlow = postsToApproveStateFlow.asStateFlow()


    private val postsStateFlow: MutableStateFlow<List<Post>> = MutableStateFlow(feedPosts)
    val postsFlow = postsStateFlow.asStateFlow()

    private val groupsStateFlow: MutableStateFlow<List<Group>> = MutableStateFlow(groups)
    val groupsFlow = groupsStateFlow.asStateFlow()

    fun approvePost(post: Post) {
        val posts = postsStateFlow.value.toMutableList()
        posts.add(post)
        postsStateFlow.value = posts
        feedPosts = feedPosts + post

        removePost(post)
    }

    fun addPost(post:Post) {
        postsToApproveStateFlow.value = postsToApproveStateFlow.value + post
    }

    fun removePost(post: Post) {
        val posts = postsToApproveStateFlow.value.toMutableList()
        posts.remove(post)
        postsToApproveStateFlow.value = posts
    }


    fun addComment(postId: Int, comment: Comment) {
        postsStateFlow.value = postsStateFlow.value.map { post ->
            if (post.id == postId) {
                post.copy(comments = post.comments.apply { add(comment) })
            } else {
                post
            }
        }.toMutableList()

        feedPosts[postId].comments.add(comment)
    }

    fun flagPost(position: Int, flag: Flag) {
       postsToApproveStateFlow.value[position].flag = flag
    }

    fun filterPosts(filter: PostType?) {
        if(filter == null) {
            postsStateFlow.value = feedPosts
        } else {
            postsStateFlow.value = feedPosts.filter { it.type == filter }
        }
    }

    fun filterByKeyword(keyword: String) {
        postsStateFlow.value = feedPosts.filter { post ->
            post.username.contains(keyword) || post.text.contains(keyword)
        }.toMutableList()
    }

    fun filterPostsToApproveByKeyword(keyword: String) {
        postsToApproveStateFlow.value = postsToApproveStateFlow.value.filter { post ->
            post.username.contains(keyword) || post.text.contains(keyword)
        }.toMutableList()
    }

    fun addGroup(group: Group) {
        groupsStateFlow.value = groupsStateFlow.value + group
        groups = groups + group
    }

    fun filterGroupsByKeyword(keyword: String) {
        groupsStateFlow.value = groups.filter { group ->
            group.groupName.contains(keyword) || group.groupDescription.contains(keyword)
        }.toMutableList()
        println(groupsStateFlow.value.size)
    }

    fun addUserToGroup(group: Group) {
        group.users.add(loggedInUser)
    }

    fun isUserInGroup(group: Group): Boolean {
        return group.users.contains(loggedInUser)
    }

}