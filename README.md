##Android6.0------权限申请~easypermissions 
 <p>前面写了Android6.0权限介绍和权限单个，多个申请，用的是纯Java代码，本文主要说的是借助第三方库来实现权限申请。</p> 
<p>借助第三方库<strong> easypermissions</strong>来申请6.0权限，Google官方推荐的。</p> 
<span id="OSC_h1_1"></span>
<h3><strong><strong><strong>easypermissions</strong>库地址：</strong></strong><a href="https://github.com/googlesamples/easypermissions" target="_blank" rel="nofollow">https://github.com/googlesamples/easypermissions</a></h3> 
<p>bulid.gradle引入：</p> 
<pre><code class="language-java">
compile 'pub.devrel:easypermissions:1.0.0'

</code></pre> 
<p>权限相关知识，权限表请看博客：</p> 
<p>&nbsp; <a href="https://my.oschina.net/zhangqie/blog/1540896" target="_blank" rel="nofollow">Android6.0------权限管理&nbsp; &nbsp;</a> &nbsp; &nbsp;</p> 
<p>&nbsp; <a href="https://my.oschina.net/zhangqie/blog/1541599" target="_blank" rel="nofollow">Android6.0------权限申请管理（单个权限和多个权限申请）</a></p> 
<p><a href="https://my.oschina.net/zhangqie/blog/1542096" target="_blank" rel="nofollow">&nbsp; Android6.0------权限申请RxPermissions</a></p> 
<p>前提：APP运行在<code>Android 6.0 (API level 23)</code>或者更高级别的设备中，而且<code>targetSdkVersion&gt;=23</code>时，系统将会自动采用动态权限管理策略，</p> 
<p>先来看看效果图：（<strong><span style="color:#800000">注：如果未授权就点击打电话或拍照就会直接闪退，由此6.0必须手动授权，开发时如果未授权，可以判断并提示用户从新授权</span></strong>）</p> 
<p>&nbsp;&nbsp;&nbsp;&nbsp; <img alt="" src="http://images2017.cnblogs.com/blog/1041439/201709/1041439-20170922145839728-1969100010.gif" width="500"></p> 
<p>&nbsp;</p> 
<p>案例主要有 电话，SD卡，拍照授权三个一起授权</p> 
<p>通过一个数组把要申请的权限放在一起，然后申请</p> 
<pre><code class="language-java">String[] perms = {Manifest.permission.CAMERA,
Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CALL_PHONE};</code></pre> 
<p>申请权限代码：</p> 
<pre><code class="language-java">private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.CAMERA,
                 Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CALL_PHONE};
        if (EasyPermissions.hasPermissions(this, perms)) {//检查是否获取该权限
             Toast.makeText(MainActivity.this,"已经获取权限了",Toast.LENGTH_LONG).show();
        } else {
            //第二个参数是被拒绝后再次申请该权限的解释
            //第三个参数是请求码
            //第四个参数是要申请的权限
            EasyPermissions.requestPermissions(this, "获取权限",
                    RC_CAMERA_AND_LOCATION, perms);
        }
    }

　　　//接收权限处理结果
    @Override
    public void onRequestPermissionsResult(int requestCode, 
                       @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //把申请权限的回调交由EasyPermissions处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List&lt;String&gt; perms) {
        Log.i("TAG","获取成功的权限有："+perms);
        Toast.makeText(MainActivity.this,"获取权限成功",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List&lt;String&gt; perms) {
        Toast.makeText(MainActivity.this,"未获取的权限"+perms,Toast.LENGTH_LONG).show();
    }</code></pre> 
<p>前提一定要注意：AndroidManifest中：</p> 
<pre><code class="language-html">  &lt;uses-permission android:name="android.permission.CALL_PHONE"/&gt;  //电话
  &lt;uses-permission android:name="android.permission.CAMERA"/&gt;    //拍照
  &lt;uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/&gt;     //sd卡</code></pre> 
<p>此案例是借助Google推荐的第三方easypermissions来写的了，可以去看看这个库的代码。</p> 
<p>&nbsp;</p> 
