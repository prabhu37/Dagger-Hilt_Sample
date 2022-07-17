package com.prabhu.socialmediatest.view.image

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.prabhu.socialmediatest.data.MessageEvent
import com.prabhu.socialmediatest.databinding.FragmentImagesBinding
import com.prabhu.socialmediatest.utils.Status
import com.prabhu.socialmediatest.view.main.MediaAdapter
import com.prabhu.socialmediatest.view.main.UploadViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode




@AndroidEntryPoint
class MediaFragment : Fragment() {
    private val imageViewModel : MediaViewModel by viewModels()
    private lateinit var fragmentImagesBinding: FragmentImagesBinding
    private lateinit var mediaAdapter: MediaAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        fragmentImagesBinding = FragmentImagesBinding.inflate(inflater, container, false)
        return fragmentImagesBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        fragmentImagesBinding.rvImages.layoutManager = layoutManager
        fragmentImagesBinding.rvImages.setHasFixedSize(true)
        mediaAdapter = MediaAdapter()
        fragmentImagesBinding.rvImages.adapter = mediaAdapter
        //fetchImages()
    }
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onNewEvent(event: MessageEvent) {
       if(event.value==0){
           fetchImages()

       }
    }



    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)
    }

    fun fetchImages(){
        imageViewModel.fetchImages("null").observe(viewLifecycleOwner,{
            when (it.status) {
                Status.SUCCESS -> {
                    fragmentImagesBinding.pBImages.visibility=View.GONE
                    mediaAdapter.submitList(it.data!!.data.filter { it.file_type=="0" })
                }
                Status.LOADING -> {
                    fragmentImagesBinding.pBImages.visibility=View.VISIBLE
                }
                Status.ERROR -> {
                    fragmentImagesBinding.pBImages.visibility=View.GONE
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


}