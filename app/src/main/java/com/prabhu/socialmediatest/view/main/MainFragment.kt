package com.prabhu.socialmediatest.view.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.prabhu.socialmediatest.R
import com.prabhu.socialmediatest.data.MessageEvent
import com.prabhu.socialmediatest.databinding.FragmentMainBinding
import com.prabhu.socialmediatest.utils.NetworkHelper
import com.prabhu.socialmediatest.utils.Status
import com.prabhu.socialmediatest.utils.Utils
import com.prabhu.socialmediatest.view.image.MediaFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.view.*
import org.greenrobot.eventbus.EventBus
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() ,View.OnClickListener{
    lateinit var fragmentMainBinding: FragmentMainBinding
    private val uploadViewModel : UploadViewModel by viewModels()
    @Inject
    lateinit var networkHelper: NetworkHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentMainBinding = FragmentMainBinding.inflate(inflater, container, false)
        return fragmentMainBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUI()
    }

    private fun setUI() {

        val viewPagerAdapter = fragmentManager?.let { ViewPagerAdapter(it) }

        viewPagerAdapter!!.add(MediaFragment(),"MEDIA")
       // viewPagerAdapter.add(VideosFragment(),"Videos")

        fragmentMainBinding.viewPager.adapter = viewPagerAdapter
        fragmentMainBinding.fabUpload.setOnClickListener(this)


        if(networkHelper.isNetworkConnected()) {
            fragmentMainBinding.tvError.visibility = View.GONE
            fragmentMainBinding.tabLayout.setupWithViewPager(fragmentMainBinding.viewPager)
        } else
            fragmentMainBinding.tvError.visibility = View.VISIBLE



    }

    private fun uploadImage(file: File) {
        uploadViewModel.uploadMedia(file).observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                   fragmentMainBinding.pBMain.visibility = View.GONE
                    if(it.data!!.data[0].file_type.equals("1")) {
                        fragmentMainBinding.viewPager.currentItem = 1
                        EventBus.getDefault().post(MessageEvent(1))
                    }
                    else {
                        fragmentMainBinding.viewPager.currentItem = 0
                        EventBus.getDefault().post(MessageEvent(0))
                    }

                }
                Status.LOADING -> {
                    fragmentMainBinding.pBMain.visibility = View.VISIBLE

                }
                Status.ERROR -> {
                    fragmentMainBinding.pBMain.visibility = View.GONE
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == Utils.PICK_MEDIA) {
            val imageUri: Uri? = data!!.data
            imageUri?.let { activity?.let { it1 -> Utils.getPath(it, it1) } }
            activity?.let {
                Utils.getPath(it,imageUri!!)?.let {
                    uploadImage(File(it))
                }
            }
        }
    }
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.fabUpload->{
                Navigation.findNavController(v.fabUpload).navigate(R.id.action_mainFragment_to_playerFragment)

                //val intent = Intent(Intent.ACTION_PICK).apply {
                //   this.type="*/*"
                 //  this.putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/*", "video/*")) }
              // startActivityForResult(intent, Utils.PICK_MEDIA, null)
            }
        }
    }
}