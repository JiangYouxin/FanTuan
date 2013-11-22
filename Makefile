all:
	ant release 

install:
	adb install -r bin/MainActivity-release.apk

clean:
	ant clean

