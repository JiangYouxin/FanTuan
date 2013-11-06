all:
	ant debug

install:
	adb install -r bin/MainActivity-debug.apk

clean:
	ant clean

