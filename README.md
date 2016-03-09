Messaging Forwarder - A simple Android app for forwarding text messages to a web server
=======================================================================================

This Android application simply uses HTTP POST to forward messages comming from a specified phone
number to a specified HTTP server as plain text.

The message will, thanks to how Android works, still show up in the default messaging application.

If the message cannot be forwarded eg. because the given HTTP server is not reachable, the
application will silently not do anything apart from logging a message in the Android system log.

I have used this to forward one time codes to scripts to avoid typing them in manually, but of
course there might be other uses.

License
-------
"THE BEER-WARE LICENSE" (Revision 42):
 <laudrup@stacktrace.dk> wrote this code.  As long as you retain this notice you
 can do whatever you want with this stuff. If we meet some day, and you think
 this stuff is worth it, you can buy me a beer in return.       Kasper Laudrup

Icons made by Visual Pharm <https://icons8.com/> under "CC BY-ND 3.0" license.
