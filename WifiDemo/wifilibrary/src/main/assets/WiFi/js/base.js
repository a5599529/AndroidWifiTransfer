var pdf_url='';
getDefaltUrl = (function(){
    return{
        my_url: '',
        set: function(fileIndex){
            var DEFAULT_URL = "";
            var PDFData = "";
            var rawLength= "";
            var array = ""; 
            $.ajax({  
                type: "get",  
                async: false,
                mimeType: 'text/plain; charset=x-user-defined',  
                url: pdf_url+'/getFile/'+fileIndex,  
                success: function(data) {  
                   PDFData = data;  
                }  
            });  
            rawLength = PDFData.length;  
            array = new Uint8Array(new ArrayBuffer(rawLength));    
            for(i = 0; i < rawLength; i++) {  
              array[i] = PDFData.charCodeAt(i) & 0xff;  
            }  
            DEFAULT_URL = array;
            this.my_url = array; 
            return  DEFAULT_URL;
            
        }

    }

})(window);

function GetQueryString(name)
{
 var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
 var r = window.location.search.substr(1).match(reg);
 if(r!=null)return  unescape(r[2]); return null;
}
getDefaltUrl.set(GetQueryString('value'));

