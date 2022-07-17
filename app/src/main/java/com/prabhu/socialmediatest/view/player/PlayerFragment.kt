package com.app.videopreloading.ui.player

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.HttpDataSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.prabhu.socialmediatest.App
import com.prabhu.socialmediatest.databinding.PlayerFragmentBinding
import com.prabhu.socialmediatest.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayerFragment : Fragment() {
    private lateinit var playerFragmentBinding :PlayerFragmentBinding
    private var videoUrl: String? = null
    private lateinit var httpDataSourceFactory: HttpDataSource.Factory
    private lateinit var defaultDataSourceFactory: DefaultDataSourceFactory
    private lateinit var cacheDataSourceFactory: DataSource.Factory
    private lateinit var simpleExoPlayer: SimpleExoPlayer
    private val simpleCache: SimpleCache = App.simpleCache



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        playerFragmentBinding = PlayerFragmentBinding.inflate(inflater, container, false)
        return playerFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //videoUrl = arguments?.getString(Constants.VIDEO_URL)

       // initPlayer()
    }
    private fun initPlayer() {
        /*httpDataSourceFactory = DefaultHttpDataSource.Factory()
            .setAllowCrossProtocolRedirects(true)

        defaultDataSourceFactory = DefaultDataSourceFactory(
            requireContext(), httpDataSourceFactory
        )

        cacheDataSourceFactory = CacheDataSource.Factory()
            .setCache(simpleCache)
            .setUpstreamDataSourceFactory(httpDataSourceFactory)
            .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)

        simpleExoPlayer = SimpleExoPlayer.Builder(requireContext())
            .setMediaSourceFactory(DefaultMediaSourceFactory(cacheDataSourceFactory)).build()

        val videoUri = Uri.parse(videoUrl)
        val mediaItem = MediaItem.fromUri(videoUri)
        val mediaSource = ProgressiveMediaSource.Factory(cacheDataSourceFactory).createMediaSource(mediaItem)

        playerFragmentBinding.playerView.player = simpleExoPlayer
        simpleExoPlayer.playWhenReady = true
        simpleExoPlayer.seekTo(0, 0)
        simpleExoPlayer.repeatMode = Player.REPEAT_MODE_OFF
        simpleExoPlayer.setMediaSource(mediaSource, true)
        simpleExoPlayer.prepare()*/
    }
}
