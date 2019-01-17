 $( "#calendarList" ).change(function() {
	 var selected=$( "#calendarList option:selected" );
     
     $("#content").text('');
     
     if(selected.text()=="all"){
    	 loadCalendarsContent();
     }else{
    	 var calendar={
    			 		"id":selected.attr('value'),
    			 		"summary":selected.text(),
    			 		"backgroundColor":selected.attr('data-color')}
    	 $(".fa-spinner").css("display","block");
    	 listUpcomingEvents(calendar);
    	 $(".fa-spinner").css("display","none");
     }
    
});



// Client ID and API key from the Developer Console
      /*
          //AIzaSyDDkiliNkCUdFLUa4xDj2S8PM1JkXDWwCE
	// client id : 794933248634-qau2th88a58hmhuatqvuu7fhgsvb9mrh.apps.googleusercontent.com
      */
      var CLIENT_ID = '794933248634-qs0i6uhrp6r01r7jaegn1t992q2r8uli.apps.googleusercontent.com';
      var API_KEY = 'AIzaSyDDkiliNkCUdFLUa4xDj2S8PM1JkXDWwCE';

      // Array of API discovery doc URLs for APIs used by the quickstart
      var DISCOVERY_DOCS = ["https://www.googleapis.com/discovery/v1/apis/calendar/v3/rest"];

      // Authorization scopes required by the API; multiple scopes can be
      // included, separated by spaces.
      var SCOPES = "https://www.googleapis.com/auth/calendar.readonly";

      var authorizeButton = document.getElementById('authorize_button');
      var signoutButton = document.getElementById('signout_button');

      /**
       *  On load, called to load the auth2 library and API client library.
       */
      function handleClientLoad() {
        gapi.load('client:auth2', initClient);
      }

      /**
       *  Initializes the API client library and sets up sign-in state
       *  listeners.
       */
      function initClient() {
        gapi.client.init({
          apiKey: API_KEY,
          clientId: CLIENT_ID,
          discoveryDocs: DISCOVERY_DOCS,
          scope: SCOPES
        }).then(function () {
          // Listen for sign-in state changes.
          gapi.auth2.getAuthInstance().isSignedIn.listen(updateSigninStatus);

          // Handle the initial sign-in state.
          updateSigninStatus(gapi.auth2.getAuthInstance().isSignedIn.get());
          authorizeButton.onclick = handleAuthClick;
          signoutButton.onclick = handleSignoutClick;
        }, function(error) {
          appendPre(JSON.stringify(error, null, 2));
        });
      }

      /**
       *  Called when the signed in status changes, to update the UI
       *  appropriately. After a sign-in, the API is called.
       */
      function updateSigninStatus(isSignedIn) {
        if (isSignedIn) {
          authorizeButton.style.display = 'none';
          signoutButton.style.display = 'block';
          
          //listUpcomingEvents();
          loadCalendarsContent();
        } else {
          authorizeButton.style.display = 'block';
          signoutButton.style.display = 'none';
        }
      }

      /**
       *  Sign in the user upon button click.
       */
      function handleAuthClick(event) {
        gapi.auth2.getAuthInstance().signIn();
      }

      /**
       *  Sign out the user upon button click.
       */
      function handleSignoutClick(event) {
        gapi.auth2.getAuthInstance().signOut();
      }

      /**
       * Append a pre element to the body containing the given message
       * as its text node. Used to display the results of the API call.
       *
       * @param {string} message Text to be placed in pre element.
       */
      function appendPre(nom,date,calendar) {
    	 
        var pre = document.getElementById('content');
        var html = pre.innerHTML;
        var newHtml='<tr style="background-color:'+calendar.backgroundColor+'"><td>'+nom+'</td><td>'+date+'</td><td>'+calendar.summary+'</td></tr>';
        pre.innerHTML=html+newHtml;
      }

      /**
       * Print the summary and start datetime/date of the next ten events in
       * the authorized user's calendar. If no events are found an
       * appropriate message is printed.
       */
      var loadCalendarsContent=function(){
    	  $(".fa-spinner").css("display","block");
    	  var request = gapi.client.calendar.calendarList.list();
          request.execute(function(resp){
	             var calendars = resp.items;
	             $('#calendarList').text("");
	             $('#calendarList').append('<option>all</option>');
	             for(var i=0;i<calendars.length;i++){
	            	 var form=$("#filterForm")
	            	 $('#calendarList').append('<option data-color='+calendars[i].backgroundColor+' value='+calendars[i].id+'>'+calendars[i].summary+'</option>');
	            	 listUpcomingEvents(calendars[i]);
	             }
	             $(".fa-spinner").css("display","none");  
	      });
          
      }
    
       
      function listUpcomingEvents(calendar) {
    	  
    	  
    	  
    	  
        gapi.client.calendar.events.list({
          'calendarId': calendar.id,
          'timeMin': (new Date()).toISOString() ,
          'showDeleted': true,
          'singleEvents': true,
          'maxResults': 100,
          'orderBy': 'startTime',
           "kind": "calendar#calendarListEntry"
        }).then(function(response) {
          var events = response.result.items;
         
          if (events.length > 0) {
            for (i = 0; i < events.length; i++) {
              var event = events[i];
              var when = event.start.dateTime;
              if (!when) {
                when = event.start.date;
              }
              appendPre(event.summary , when ,calendar)
            }
          } else {
            appendPre('----------------','---------------',calendar);
          }
        });
      }
