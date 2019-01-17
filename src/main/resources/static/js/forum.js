 $("#reply").on("click",function(e){
            	  e.preventDefault();
            	  var id=$("#reply").attr('data-id');
            	  var form =$("#addReplyForm");
            	  form.attr('action',form.attr('data-action')+"add?id="+id);
            	  $("#reponse").attr("value","");
            	  $("#reponse").text("");
            	  $("#AddReplyModal").modal("show");
              })
   $(".edit-reply").on("click",function(e){
            	  e.preventDefault();
            	  var id=$(this).attr('data-id');
            	  var reponse = $("#"+id).text();
            	  var form =$("#addReplyForm");
            	  $("#reponse").attr("value",reponse);
            	  $("#reponse").text(reponse);
            	  form.attr('action',form.attr('data-action')+"edit?id="+id);
            	  
            	  $("#AddReplyModal").modal("show");
              })            
      
  $("#addQuestion").on("click",function(e){
            	  e.preventDefault();
            	  var form =$("#form");
            	  form.attr('action',form.attr('data-action')+"add");
            	  $("#sujet").attr("value","");
            	  $("#sujet").text("");
            	  $("#desc").attr("value","");
            	  $("#desc").text("");
            	  $("#addModal").modal("show");
              })
    
  $(".editQuestion").on("click",function(e){
            	  e.preventDefault();
            	  var id=$(this).attr('data-id');
            	  var sujet = $("#sujet"+id).text();
            	  var desc = $("#desc"+id).text();
            	  var form =$("#form");
            	  
            	  $("#sujet").attr("value",sujet);
            	  $("#sujet").text(sujet);
            	  $("#desc").attr("value",desc);
            	  $("#desc").text(desc);
            	  
            	  form.attr('action',form.attr('data-action')+"edit?id="+id);
            	  
            	  $("#addModal").modal("show");
              }) 