#####################
#	PHORKPE Readme	#
#####################

PHORKPE is a java software used to remotly send key press to cisco ip phones.
This way you can send anykind of key as if you were manipulating the phone directly on premise.

This is useful for reseting stale certificate or just processing any kind of action that can
normaly only be done by hand using the phone menu.

It supports HTTP or JTAPI execute method. HTTP is faster because you can address many phones at the same time
but JTAPI needs to authenticate only once to the server, so is perfect for deleting ITL files on stale phones.

It is multithreaded

It is a rather straightforward software so there is no phone model detection
You have to know what to send according to the phone model.
Then you have to create the corresponding key press profile.

It is using the following configuration file :
- devicelist.csv :
	+ List the device to process
	+ Use the following format : Device IP;Device Name;Key Press Profile Name
	+ The IP is only used to ping the device and for HTTP request
	+ The device name is used for JTAPI request
	+ The Key Press Profile, list the key to send to the phone
- KeyPressProfileList.xml :
	+ List the key press profile
	+ A key press profile list the key to send to the phone
	+ You can also add pause
- configfile.xml :
	+ List the software parameters
