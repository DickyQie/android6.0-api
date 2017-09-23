# Android6.0------权限申请RxPermissions 

 <p>前面写了Android6.0权限介绍和权限单个，多个申请，用的是纯Java代码，本文主要说的是借助第三方库来实现权限申请。</p> 
<p>借助第三方库<strong> RxPermissions</strong>来申请6.0权限。</p> 
<span id="OSC_h3_1"></span>
<h3><strong><strong>RxPermissions库地址：</strong></strong><a href="https://github.com/tbruyelle/RxPermissions" target="_blank" rel="nofollow">https://github.com/tbruyelle/RxPermissions </a></h3> 
<p>bulid.gradle引入：</p> 
<pre><code class="language-java">
  compile 'com.tbruyelle.rxpermissions2:rxpermissions:0.9.4@aar'
  compile "io.reactivex.rxjava2:rxjava:2.0.0"

</code></pre> 
<p>权限相关知识，权限表请看博客：</p> 
<p>&nbsp; <a href="https://my.oschina.net/zhangqie/blog/1540896" target="_blank" rel="nofollow">Android6.0------权限管理&nbsp; &nbsp;</a> &nbsp; &nbsp;</p> 
<p>&nbsp; <a href="https://my.oschina.net/zhangqie/blog/1541599" target="_blank" rel="nofollow">Android6.0------权限申请管理（单个权限和多个权限申请）</a></p> 
<p>前提：APP运行在<code>Android 6.0 (API level 23)</code>或者更高级别的设备中，而且<code>targetSdkVersion&gt;=23</code>时，系统将会自动采用动态权限管理策略，</p> 
<p>先来看看效果图：（<strong><span style="color:#800000">注：如果未授权就点击打电话或拍照就会直接闪退，由此6.0必须手动授权，开发时如果未授权，可以判断并提示用户从新授权</span></strong>）</p> 
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <img alt="" src="https://static.oschina.net/uploads/img/201709/23083142_V4WL.gif" width="500"></p> 
<p>&nbsp;</p> 
<p>&nbsp;</p> 
<p>上图：</p> 
<p>1：单个授权,电话授权。</p> 
<p>2：有电话，SD卡，拍照授权三个一起授权</p> 
<p>&nbsp;</p> 
<p>单个授权</p> 
<pre><code class="language-java">//检查版本是否大于M
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
                                        showToast("同意权限");
                                    }else {
                                        showToast("拒绝权限");
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }</code></pre> 
<p>多个授权</p> 
<pre><code class="language-java">rxPermissions.requestEach(Manifest.permission.CAMERA,
Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CALL_PHONE)
                         .subscribe(new Observer&lt;Permission&gt;() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }
                            @Override
                            public void onNext(Permission permission) {

                                if (permission.name.equals(Manifest.permission.CAMERA)){
                                    showToast("申请成功");
                                }
                            }
                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });</code></pre> 
<p>前提一定要注意：AndroidManifest中：</p> 
<pre><code class="language-html">&lt;uses-permission android:name="android.permission.CALL_PHONE"/&gt;  //电话
&lt;uses-permission android:name="android.permission.CAMERA"/&gt;    //拍照
&lt;uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/&gt;     //sd卡</code></pre> 
<p>此案例是借助第三方RxPermissions来写的了，可以去看看这个库的代码。</p> 
