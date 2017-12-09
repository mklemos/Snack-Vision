
Connector and component view
- DJango Server (hosted on google cloud.)
 	-JSON get request
 	-
- User interface (downloaded off google play.)
	- JSON post request



1.  A user session authenticates, which sends a get() request to the server.
2.  The nodes saved in the server are served to the client.
3.  A map is created from googles map API.  
4.  Nodes are added to the map and color coded.
5.  when a user adds a new node to the map, it sends a post() request to the server.
6.  the node is saved via SQL query.