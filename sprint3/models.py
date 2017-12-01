from django.db import models

# Create your models here. Jack version!

#standard user
Class User(models.Model):
	#auto incrementing id.  set as primary key.
	user_id_token = models.AutoField(primary_key=True)

	user_is_student = models.BooleanField()
	user_push_notify = models.BooleanField()

	user_push_token = models.CharField(max_length = 200)
	#validates using emailValidator -- just checks for @something.com
	user_email = models.EmailField(max_length = 50)

Class Node(models.Model):
	#auto incrementing id.  set as primary key.
	node_id_token = models.AutoField(primary_key=True)

	node_name = models.CharField(max_length = 40)
	#seperated into two entries for ease of use.
	node_latitude = models.FloatField()
	node_longitude = models.FloatField() 

	#needed for Event Nodes -- first we set constants. 
	general_node_constant = 'G'
	event_node_constant = 'E'
	#selection will be one char long, E or G.
	node_type_choices = (
		(general_node_constant, 'General node'),
		(event_node_constant, 'Event node')
	)
	#finally a charfield takes input.
	node_type = models.CharField(
								max_length = 1,
								choices = node_type_choices,
								default = general_node)
	#end node_type

	#
	node_creation_time = models.DateTimeField(auto_now=True, auto_now_add=True)

	#subclass : event
	node_event_description = models.CharField(max_length = 200)
	node_event_startime = models.DateTimeField()
	node_event_endtime = models.DateTimeField()

	#keys
	#this will require that a user is tied to each node created. I hope.
	user = models.ForeignKey(User) 



	#once finished, remember to python manage.py makemigrations <app name>

