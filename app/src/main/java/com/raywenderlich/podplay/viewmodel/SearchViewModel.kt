package com.raywenderlich.podplay.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.raywenderlich.podplay.repository.ItunesRepo
import com.raywenderlich.podplay.service.ItunesService
import com.raywenderlich.podplay.service.PodcastResponse

class SearchViewModel(application: Application) :
    AndroidViewModel(application) {

    var iTunesRepo: ItunesRepo? =  null

    data class PodcastSummaryViewData(
        var name: String? = "",
        var lastUpdated: String? = "",
        var imageUrl: String? = "",
        var feedUrl: String? = "")

    private fun itunesPodcastToPodcastSummaryView(
        itunesPodcast: PodcastResponse.ItunesPodcast):
            PodcastSummaryViewData {
        return PodcastSummaryViewData(
            itunesPodcast.collectionCensoredName,
            itunesPodcast.releaseDate,
            itunesPodcast.artworkUrl30,
            itunesPodcast.feedUrl)
    }

    fun searchPodcasts(term: String, callback: (List<PodcastSummaryViewData>) -> Unit)      {
        iTunesRepo?.searchByTerm(term) { results ->
            if (results == null) {
                callback(emptyList())
            } else {
                val searchViews = results.map { podcast ->
                    itunesPodcastToPodcastSummaryView(podcast)
                }

                callback(searchViews)
            }
        }
    }

    private fun setupViewModels() {
        val service = ItunesService.instance
        //searchViewModel.iTunesRepo = ItunesRepo(service)
    }
}