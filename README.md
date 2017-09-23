# Android6.0------权限申请RxPermissions 

<div id="cnblogs_post_body"><p>前面写了Android6.0权限介绍和权限单个，多个申请，用的是纯Java代码，本文主要说的是借助第三方库来实现权限申请。</p>
<p>借助第三方库<span style="font-size: 16px"><strong> <span class="path-divider">RxPermissions</span></strong><span class="path-divider">来申请6.0权限。</span></span></p>
<h1 class="public "><span class="path-divider"><strong><span style="font-size: 14px"><strong> <span class="path-divider">RxPermissions库地址：</span></strong></span></strong><a href="https://github.com/tbruyelle/RxPermissions" target="_blank"><span style="font-size: 14px"><span class="path-divider">https://github.com/tbruyelle/RxPermissions</span></span> </a></span></h1>
<p>bulid.gradle引入：</p>
<div class="cnblogs_code">
<pre><span style="color: #008080">  compile</span> <span style="color: #ff6600">'com.tbruyelle.rxpermissions2:rxpermissions:0.9.4@aar'</span><span style="color: #000000"><span style="color: #008080">
  compile</span> </span><span style="color: #ff6600">"io.reactivex.rxjava2:rxjava:2.0.0"</span></pre>
</div>
<p>&nbsp;</p>
<p><span style="font-size: 14px">权限相关知识，权限表请看博客： <a href="http://www.cnblogs.com/zhangqie/p/7562736.html" target="_blank"> <span class="postTitle2">Android6.0------权限管理&nbsp; &nbsp;</span></a><span class="postTitle2"> &nbsp; &nbsp; &nbsp; <a href="http://www.cnblogs.com/zhangqie/p/7562959.html" target="_blank"><span class="postTitle2">Android6.0------权限申请管理（单个权限和多个权限申请）</span></a></span></span></p>
<p><span style="font-size: 14px"><span class="postTitle2">前提：APP运行在<code>Android 6.0 (API level 23)</code>或者更高级别的设备中，而且<code>targetSdkVersion&gt;=23</code>时，系统将会自动采用动态权限管理策略，</span></span></p>
<p><span style="font-size: 14px"><span class="postTitle2">先来看看效果图：（<strong><span style="color: #800000">注：如果未授权就点击打电话或拍照就会直接闪退，由此6.0必须手动授权，开发时如果未授权，可以判断并提示用户从新授权</span></strong>）</span></span></p>
<p><span style="font-size: 14px"><span class="postTitle2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <img src="http://images2017.cnblogs.com/blog/1041439/201709/1041439-20170922114844400-185031241.gif" alt="" width="500"></span></span></p>
<p>&nbsp;</p>
<p><span style="font-size: 14px"><span class="postTitle2">上图：</span></span></p>
<p>&nbsp;</p>
<p><span style="font-size: 14px"><span class="postTitle2">1：单个授权,电话授权。</span></span></p>
<p><span style="font-size: 14px"><span class="postTitle2">2：有电话，SD卡，拍照授权三个一起授权</span></span></p>
<p><span style="font-size: 14px">单个授权</span></p>
<pre name="code" class="java">//检查版本是否大于M
                if (Build.VERSION.SDK_INT &gt;= Build.VERSION_CODES.M) {
                   //单个权限

                    rxPermissions.request(Manifest.permission.CAMERA)
                            .subscribe(new Observer&lt;Boolean&gt;() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }
                                @Override
                                public void onNext(Boolean value) {
                                    if(value){
                                        showToast(&quot;同意权限&quot;);
                                    }else {
                                        showToast(&quot;拒绝权限&quot;);
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }</pre><br>
<span style="font-size:14px">多个授权</span>
<pre name="code" class="java">rxPermissions.requestEach(Manifest.permission.CAMERA,
     Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CALL_PHONE)
                         .subscribe(new Observer&lt;Permission&gt;() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }
                            @Override
                            public void onNext(Permission permission) {

                                if (permission.name.equals(Manifest.permission.CAMERA)){
                                    showToast(&quot;申请成功&quot;);
                                }
                            }
                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
</pre><br>
<p><span style="font-size: 14px">前提一定要注意：AndroidManifest中：</span></p>
<pre name="code" style="font-size:14px" class="html">  &lt;uses-permission android:name=&quot;android.permission.CALL_PHONE&quot;/&gt;  //电话
  &lt;uses-permission android:name=&quot;android.permission.CAMERA&quot;/&gt;    //拍照
  &lt;uses-permission android:name=&quot;android.permission.WRITE_EXTERNAL_STORAGE&quot;/&gt;     //sd卡
</pre><br>
<p>此案例是借助第三方RxPermissions来写的了，可以去看看这个库的代码。</p>
