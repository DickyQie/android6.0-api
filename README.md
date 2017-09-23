# Android6.0------权限申请RxPermissions 

<div id="cnblogs_post_body"><p>前面写了Android6.0权限介绍和权限单个，多个申请，用的是纯Java代码，本文主要说的是借助第三方库来实现权限申请。</p>
<p>借助第三方库<span style="font-size: 16px"><strong> <span class="path-divider">RxPermissions</span></strong><span class="path-divider">来申请6.0权限。</span></span></p>
<h1 class="public "><span class="path-divider"><strong><span style="font-size: 16px"><strong> <span class="path-divider">RxPermissions库地址：</span></strong></span></strong><a href="https://github.com/tbruyelle/RxPermissions" target="_blank"><span style="font-size: 16px"><span class="path-divider">https://github.com/tbruyelle/RxPermissions</span></span> </a></span></h1>
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
<p>&nbsp;</p>
<p><span style="font-size: 14px"><span class="postTitle2">上图：</span></span></p>
<p>&nbsp;</p>
<p><span style="font-size: 14px"><span class="postTitle2">1：单个授权,电话授权。</span></span></p>
<p>&nbsp;</p>
<p><span style="font-size: 14px"><span class="postTitle2">2：有电话，SD卡，拍照授权三个一起授权</span></span></p>
<p>&nbsp;</p>
<p><span style="font-size: 14px">单个授权</span></p>
<p><span style="font-size: 14px">&nbsp;</span></p>
<div class="cnblogs_code">
<pre> <span style="color: #008000">//</span><span style="color: #008000">检查版本是否大于M</span>
                <span style="color: #0000ff">if</span> (Build.VERSION.SDK_INT &gt;=<span style="color: #000000"> Build.VERSION_CODES.M) {
                   </span><span style="color: #008000">//</span><span style="color: #008000">单个权限</span>
<span style="color: #000000">
                    rxPermissions.request(Manifest.permission.CAMERA)
                            .subscribe(</span><span style="color: #0000ff">new</span> Observer&lt;Boolean&gt;<span style="color: #000000">() {
                                @Override
                                </span><span style="color: #0000ff">public</span> <span style="color: #0000ff">void</span><span style="color: #000000"> onSubscribe(Disposable d) {

                                }
                                @Override
                                </span><span style="color: #0000ff">public</span> <span style="color: #0000ff">void</span><span style="color: #000000"> onNext(Boolean value) {
                                    </span><span style="color: #0000ff">if</span><span style="color: #000000">(value){
                                        showToast(</span>"同意权限"<span style="color: #000000">);
                                    }</span><span style="color: #0000ff">else</span><span style="color: #000000"> {
                                        showToast(</span>"拒绝权限"<span style="color: #000000">);
                                    }
                                }

                                @Override
                                </span><span style="color: #0000ff">public</span> <span style="color: #0000ff">void</span><span style="color: #000000"> onError(Throwable e) {

                                }

                                @Override
                                </span><span style="color: #0000ff">public</span> <span style="color: #0000ff">void</span><span style="color: #000000"> onComplete() {

                                }
                            });
                }</span></pre>
</div>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>多个授权</p>
<div class="cnblogs_code">
<pre><span style="color: #000000">　　　　　　　 rxPermissions.requestEach(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CALL_PHONE)
                         .subscribe(</span><span style="color: #0000ff">new</span> Observer&lt;Permission&gt;<span style="color: #000000">() {
                            @Override
                            </span><span style="color: #0000ff">public</span> <span style="color: #0000ff">void</span><span style="color: #000000"> onSubscribe(Disposable d) {

                            }
                            @Override
                            </span><span style="color: #0000ff">public</span> <span style="color: #0000ff">void</span><span style="color: #000000"> onNext(Permission permission) {

                                </span><span style="color: #0000ff">if</span><span style="color: #000000"> (permission.name.equals(Manifest.permission.CAMERA)){
                                    showToast(</span>"申请成功"<span style="color: #000000">);
                                }
                            }
                            @Override
                            </span><span style="color: #0000ff">public</span> <span style="color: #0000ff">void</span><span style="color: #000000"> onError(Throwable e) {

                            }

                            @Override
                            </span><span style="color: #0000ff">public</span> <span style="color: #0000ff">void</span><span style="color: #000000"> onComplete() {

                            }
                        });</span></pre>
</div>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p><span style="font-size: 14px"><span class="postTitle2">前提一定要注意：AndroidManifest中：</span></span></p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<div class="cnblogs_code">
<pre>  <span style="color: #0000ff">&lt;<span style="color: #800000">uses-permission <span style="color: #ff0000">android:name<span style="color: #0000ff">="android.permission.CALL_PHONE"<span style="color: #0000ff">/&gt; <span style="color: #000000"> <span style="color: #008000">//<span style="color: #008000">电话
  <span style="color: #0000ff">&lt;<span style="color: #800000">uses-permission <span style="color: #ff0000">android:name<span style="color: #0000ff">="android.permission.CAMERA"<span style="color: #0000ff">/&gt;   <span style="color: #0000ff"><span style="color: #000000"> <span style="color: #008000">//<span style="color: #008000">拍照
  <span style="color: #0000ff">&lt;<span style="color: #800000">uses-permission <span style="color: #ff0000">android:name<span style="color: #0000ff">="android.permission.WRITE_EXTERNAL_STORAGE"<span style="color: #0000ff">/&gt;    <span style="color: #0000ff"><span style="color: #000000"> <span style="color: #008000">//<span style="color: #008000">sd卡</span></span></span></span></span></span></span></span></span></span></span></span></span></span></span></span></span></span></span></span></span></span></span></span></span></span></pre>
</div>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>此案例是借助第三方RxPermissions来写的了，可以去看看这个库的代码。</p>
