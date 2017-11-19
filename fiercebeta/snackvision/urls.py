from django.conf.urls import url
from snackvision import views

urlpatterns = [
    url(r'^marker/$', views.marker_list),
    url(r'^marker/(?P<pk>[0-9]+)/$', views.marker_detail),
]
