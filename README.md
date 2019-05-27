# Twilcord

Twilcord is a simple Discord bot that connects a Twilio phone number and
a Discord channel. It supports messages uploaded by Discord users,
sending them along to the linked phone number as an MMS message. When
that number sends along an image, Twilcord grabs a link to the image, 
and adds it to the beginning of their message. (Discord embeds the
image(s) on its own.)

## Getting Started
You will need:
* A Twilio phone number. Make sure at the very least it can send 
SMS and MMS messages. (Note, twilio is not free, but they give you a
trial account with $15 USD. However, all messages sent out of your trial
account include `Sent from your Twilio trial account - ` so paying for
the service is up to you.)
* A Discord account.
* Port 4567 open. (If you want HTTPS, you could probably set up a web
server to proxy to that port instead, which probably would be a better
idea anyways.)

### Configuring Twilio
1. Under Phone Numbers, select Active Numbers. Click the highlighted link.

![Step 1](1_numbers_page.png)

2. Under Messaging, there's a text box called "A MESSAGE COMES IN"
Make sure webhook is highlighted, and put `http://username:password@ip.or.domain/sms/`
in the box. Replace values as needed. It's recommend to generate a random
username and password for the auth, and to put the webserver behind https.

![Step 2](2_number_config.png)
