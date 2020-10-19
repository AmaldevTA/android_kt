package com.aml.storage

import android.app.RecoverableSecurityException
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.io.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mediaFromSharedStorage()
    }

    private fun internalFile() {
        val file = File(filesDir, "sample.txt")
        file.createNewFile()

        openFileOutput("sample.txt", Context.MODE_PRIVATE).use {
            it.write("Hello world".toByteArray())
        }

        openFileInput("sample.txt").bufferedReader().useLines { lines ->
            lines.fold("") { some, text ->
                "$some\n$text"
            }
        }
    }

    private fun internalDirectory() {
        getDir("NewFolder", Context.MODE_PRIVATE)
    }

    private fun internalCache() {
        val rootPath: String = cacheDir.absolutePath + "/MyFolder/"
        val root = File(rootPath)
        if (!root.exists()) {
            root.mkdirs()
        }
        val f = File(rootPath + "myNotes.txt")
        if (f.exists()) {
            f.delete()
        }
        f.createNewFile()

        FileOutputStream(f).use {
            it.write("Hello world".toByteArray())
        }
    }


    private fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    private fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in
                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }

    // no permission needed
    private fun externalFileAppSpecific() {
        // isExternalStorageWritable then
        //File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "sample.txt")
        val appSpecificExternalDir = File(getExternalFilesDir(null), "sample.txt")
    }

    // no permission needed
    private fun externalCacheFileAppSpecific() {
        //isExternalStorageWritable then
        val externalCacheFile = File(externalCacheDir, "sample.txt")

    }

    private fun availableExternalStorage() {
        val externalStorageVolumes: Array<out File> =
                ContextCompat.getExternalFilesDirs(applicationContext, null)
        val primaryExternalStorage = externalStorageVolumes[0]
    }

    //this need read permission
    private fun mediaFromSharedStorage() {

        val projection = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.MIME_TYPE,
                MediaStore.Images.Media.SIZE,
        )
        val selection = ("(" + MediaStore.Images.Media.MIME_TYPE
                + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?)"
                + " and " + MediaStore.Images.Media.SIZE + ">=?")
        val selectionArgs = arrayOf("image/jpeg", "image/png", "102400")
        val sortOrder = MediaStore.Images.Media.DATE_MODIFIED + " desc"

        applicationContext.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                sortOrder
        )?.use { cursor ->
            val columnIndex: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

            while (cursor.moveToNext()) {
                val origId: Long = cursor.getLong(columnIndex)
                val uri = Uri.withAppendedPath(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        origId.toString()
                )
            }
        }
    }

    private fun openMediaFile(uri: Uri) {
        val resolver = applicationContext.contentResolver

        // "rw" for read-and-write;
        // "rwt" for truncating or overwriting existing file contents.
        val readOnlyMode = "r"
        resolver.openFileDescriptor(uri, readOnlyMode).use { parcelFileDescriptor ->
            // Perform operations on "pfd".
        }

        resolver.openInputStream(uri).use { stream ->
            // Perform operations on "stream".
        }
    }

    private fun saveMediaSharedStorage() {
        val resolver = applicationContext.contentResolver

        val imageCollection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } else {
            MediaStore.Images.Media.getContentUri("external")
        }

        val imageDetails = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "MyImage.png")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }
        }

        val songContentUri = resolver.insert(imageCollection, imageDetails)

        resolver.openFileDescriptor(songContentUri!!, "w", null).use { pfd ->
            // Write data into the pending audio file.
        }

        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            imageDetails.clear()
            imageDetails.put(MediaStore.Images.Media.IS_PENDING, 0)
            resolver.update(songContentUri, imageDetails, null, null)
        }

    }

    private fun updateMediaSharedStorage(mediaId: Int) {
        val resolver = applicationContext.contentResolver

        val selection = "${MediaStore.Images.Media._ID} = ?"

        val selectionArgs = arrayOf(mediaId.toString())

        val updatedSongDetails = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "MyFavImage.png")
        }

        val numSongsUpdated = resolver.update(
                Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, mediaId.toString()),
                updatedSongDetails,
                selection,
                selectionArgs)
    }

    private fun updateMediaSharedStorageOtherApp(uri: Uri) {
        try {
            contentResolver.openFileDescriptor(uri, "w")?.use {
                // do the work
            }
        } catch (securityException: SecurityException) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val recoverableSecurityException = securityException as?
                        RecoverableSecurityException
                        ?: throw RuntimeException(securityException.message, securityException)

                val intentSender =
                        recoverableSecurityException.userAction.actionIntent.intentSender
                intentSender?.let {
                    startIntentSenderForResult(intentSender, 1001,
                            null, 0, 0, 0, null)
                    // onActivityResult requestCode - 1001
                }
            } else {
                throw RuntimeException(securityException.message, securityException)
            }
        }
    }

    private fun deleteMediaSharedStorage(){
//        val resolver = applicationContext.contentResolver
//
//        val imageUri = "..."
//
//        val selection = "..."
//        val selectionArgs = "..."
//
//        val numImagesRemoved = resolver.delete(
//                imageUri,
//                selection,
//                selectionArgs)
    }

    private fun createSharedFile(pickerInitialUri: Uri) {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/pdf"
            putExtra(Intent.EXTRA_TITLE, "invoice.pdf")

            // Optionally, specify a URI for the directory that should be opened in
            // the system file picker before your app creates the document.
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
            }
        }
        startActivityForResult(intent, 1002)
    }

    private fun openSharedFile(pickerInitialUri: Uri) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/pdf"

            // Optionally, specify a URI for the file that should appear in the
            // system file picker when it loads.
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
            }
        }

        startActivityForResult(intent, 1003)
    }


    @Throws(IOException::class)
    private fun readSharedFile(uri: Uri): String{
        val stringBuilder = StringBuilder()
        contentResolver.openInputStream(uri)?.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                var line: String? = reader.readLine()
                while (line != null) {
                    stringBuilder.append(line)
                    line = reader.readLine()
                }
            }
        }
        return stringBuilder.toString()
    }


    private fun editSharedFile(uri: Uri){
        try {
            contentResolver.openFileDescriptor(uri, "w")?.use {
                FileOutputStream(it.fileDescriptor).use {
                    it.write(
                            ("Overwritten at ${System.currentTimeMillis()}\n")
                                    .toByteArray()
                    )
                }
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun deleteSharedFile(uri: Uri){
        DocumentsContract.deleteDocument(applicationContext.contentResolver, uri)

    }

}