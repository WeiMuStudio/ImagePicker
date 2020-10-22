package io.weimu.www.imagepicker.ui

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.afollestad.assent.Permission
import com.pmm.imagepicker.ImagePicker
import com.pmm.imagepicker.ui.preview2.ImagePreviewActivity
import com.pmm.ui.core.StatusNavigationBar
import com.pmm.ui.core.recyclerview.decoration.GridItemDecoration
import com.pmm.ui.ktx.click
import com.pmm.ui.ktx.dip2px
import com.pmm.ui.ktx.requestPermission
import io.weimu.www.imagepicker.R
import io.weimu.www.imagepicker.fragment.adapter.ImageGridAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val maxImageNumber = 9
    private val spanCount = 4

    private val mAdapter: ImageGridAdapter by lazy {
        ImageGridAdapter(this, maxImageNumber).apply {
            //其他操作
            this.imageActionListener = object : ImageGridAdapter.ImageActionListener {

                override fun onItemClick(position: Int) {
                    ImagePreviewActivity.start(this@MainActivity, this@apply.dataList, position)
                }

                override fun onItemDeleteClick(position: Int) {
                    this@apply.removeItem(position)
                }

                //添加
                override fun onItemAddClick() {
                    ImagePicker.pickImage(this@MainActivity)
//                    ImagePicker.pickAvatar(this@MainActivity);
//                    ImagePicker.pickImage(this@MainActivity, 30)
//                    ImagePicker.pickImage4One(
//                            activity = this@MainActivity,
//                            cropAspectRatioY = 9,
//                            cropAspectRatioX = 16,
//                            cropMiniHeight = this@MainActivity.dip2px(100f),
//                            cropMiniWidth = this@MainActivity.dip2px(1000f)
//                    )
//                ImagePicker.takePhoto(this@MainActivity, true);//使用摄像头


//                ImagePicker.custom(this@MainActivity, Config().apply {
//                    selectMode = Config.MODE_SINGLE
//                    enableCrop = true
//                    cropAspectRatioX = 1
//                    cropAspectRatioY = 1
//                })
                }
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusNavigationBar.setStatusNavigationBarTransparent(window)
        StatusNavigationBar.setDarkMode(window, true)
        setContentView(R.layout.activity_main)

        //ToolBar
        mToolBar.apply {
            this.showStatusView = true
            this.navigationIcon {
                this.click { onBackPressed() }
            }
            this.centerTitle {
                this.text = getString(R.string.app_name)
            }
        }

        initRecyclerVIew()


        requestPermission(
                permissions = arrayOf(Permission.WRITE_EXTERNAL_STORAGE,
                        Permission.READ_EXTERNAL_STORAGE,
                        Permission.CAMERA),
                granted = { },
                message = "请给我权限！"
        )

        mAdapter.addData("https://dss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1091405991,859863778&fm=26&gp=0.jpg")
        mAdapter.addData("https://youxi-test.oss-cn-beijing.aliyuncs.com/images/847313ed-3411-40fd-aa2d-5efc8d39a364.gif")

    }

    private fun initRecyclerVIew() {
        this.id_RecyclerView.apply {
            this.adapter = mAdapter
            this.layoutManager = GridLayoutManager(this@MainActivity, spanCount)
            this.addItemDecoration(GridItemDecoration(spanCount, dip2px(8f), dip2px(8f)))
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == ImagePicker.REQUEST_IMAGE) {
            val target = data?.getSerializableExtra(ImagePicker.REQUEST_OUTPUT) as List<String>
            Log.d("imagePicker", target.toString())
            mAdapter.addData(target)
        }
    }
}
