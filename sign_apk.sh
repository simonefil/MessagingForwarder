#!/bin/bash
if [ ! -n "$1" ]; then
    echo "Usage $0 [APK NAME]";
    exit 1
fi

jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 -keystore keystore.jks "$1" MessagingForwarder
