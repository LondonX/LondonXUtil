# Lutil
Tool box of android

### Usage
1. import as module in you project
2. call `Lutil.init(Context)` in your app application

## Important classes
* Lutil
* ConnectionChecker
* FileUtil
* LMediaPlayer
* LRequestTool
* PermissionUtil
* ToastUtil

## Lutil
Main entry of Lutil tool box.  

### Usage
* call `Lutil.init(Context)` in your app application

* You should call `Lutil.init(Context)` in your app `Application` before every thing. It will do `ToastUtil.init(Context)` and `PermissionUtil.init(Context)` for you.

## ConnectionChecker
### Usage
* `isNetworkConnected()` returns `true` if network is functional
* `getNetworkType()` returns one of `WiFi`, `Cell` or `Non`

## FileUtil
### Usage
* `getMediaType(String)` returns the `MediaType` from the given `filePath`
* `getFileType(String)` returns the one of `music`, `video`, `picture`, `web`, `unknown` of the given `filePath`
* `getFileFromUri(Uri)` returns a `File` object of the given `uri`, with every `Build.VERSION.SDK_INT`
* `getDownloadFile(String)` returns a `File` object of default downloaded file of given `url`
* `createFile(File)` create the `file` with all parent
* `getCacheFolder()` returns the app's default cache dir
* `getCacheSize()` returns the cache dir size, in byte.
* `getFileSize(File)` returns the file or directory size, in byte
* `getFormattedFileSize(File)` returns the file or directory size, auto format in B, KB, MB, GB
* `cleanCache()` delete all file and directory of cache dir
### Inner classes and enum
* `FileType` with 4 value `music`, `video`, `picture`, `web`, `unknown`

## LMediaPlayer
### Usage
* `new LMediaPlayer(SurfaceView, SeekBar,MediaPlayer.OnPreparedListener)` instance the player with `SurfaceView` to present video, `SeekBar` to indicate the buffer and playing progress, and seek the media, `MediaPlayer.OnPreparedListener` to listen the async mediaPlayer preparation. All things can be `null` and set it later.
* `play()` play the media if prepared
* `prepareUrl(String)` prepare the given `url` media async, and will call `MediaPlayer.OnPreparedListener`'s method when prepared
* `playUrl(String)` prepare and play the given `url`, will call `MediaPlayer.OnPreparedListener`'s method when prepared
* `pause()` pause the media.
* `stop()` stop the media and release the player

## LRequestTool
### Usage
* `setMediaType(MediaType)` set media type such as `application/x-www-form-urlencoded;`
* `setGlobalResponseListener()` set `GlobalResponseListener` to handle the response before the local `OnResponseListener`
* `LRequestTool(OnResponseListener responseListener)` ins
* `doGet(String url, DefaultParams params, int requestCode)` do GET request async, with the given `url`, `params`(HashMap<String, Object>), and `requestCode` for callback
* `doPost` as `doGet` above
### Related classes
* `GlobalResponseListener` the global `OnResponseListener`
* `OnResponseListener` called when `LRequestTool` gets a response
* `LResponse` the response that `LRequestTool` got, `requestCode` to determine which request it comes from, `responseCode` http status code such as 200 404 500, `body` the response body with `String` object, `downloadFile` the response body with `File` object

## PermissionUtil
### Usage
* `init(Context)` initialize the PermissionUtil with app's permissions
* `init(Context,String[])` initialize the PermissionUtil with the given permissions
* `isAllPermissionAllowed()` check if all permissions is guaranteed
* `request(Activity)` and `request(Fragment)` make the permission requesting dialog, callback to `Activity`'s `onRequestPermissionsResult(...)` as normal

## ToastUtil
ToastUtil can simply show text, without wait the previous toast dismiss
### Usage
* `init(Context)` initialize the `ToastUtil`, it will be done in `Lutil.init(Context)`
* `show(int)` and `show(String)` show the text or text from R.string
* `serverErr(LResponse)` show the error message as *serverErr: 400*
