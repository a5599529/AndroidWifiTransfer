package com.hd.pdfconverter.model.LocalBean

import java.io.Serializable

data class FileEntity (var id:String, var name:String, var size:Long, var path:String,var time:Long,var type:String):Serializable
data class FileItem(var id:Int,var name:String,var path:String,var isDirectory:Boolean,var image:Int)