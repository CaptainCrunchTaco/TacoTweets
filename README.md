# TacoTweets
Using OAuth login, users can singin with their Twitter accounts and review their home timeline and post a status update.

Time spent so far: 24 hours

Completed user stories (Pre-fragments):
 * [x] Required: User can sign in to Twitter using OAuth login
 * [x] Required: User can view the tweets from their home timeline
 * [x] Required: User should be displayed the username, name, and body for each tweet
 * [x] Required: User should be displayed the relative timestamp for each tweet "8m", "7h"
 * [x] Required: User can view more tweets as they scroll with infinite pagination
 * [x] Required: User can compose a new tweet
 * [x] Required: User can click a “Compose” icon in the Action Bar on the top right
 * [x] Required: User can then enter a new tweet and post this to twitter
 * [x] Required: User is taken back to home timeline with new tweet visible in timeline
 * [x] Optional: Links in tweets are clickable and will launch the web browser
 * [x] Optional: User can see a counter with total number of characters left for tweet
 * [x] Optional Advanced: User can refresh tweets timeline by pulling down to refresh (i.e pull-to-refresh)
 * [x] Optional Advanced: User can open the twitter app offline and see last loaded tweets

Completed user stories (Post-fragments):
* [x] Required: Includes all required user stories from Week 3 Twitter Client
* [x] Required: User can switch between Home Timeline and Mentions Timeline
* [x] Required: Users can navigate to their own profile
* [x] Required: Users can click on profile image in tweet and see other people's profiles.
* [x] Required: Profiles include picture, tagline, # of followers, # of following, and tweets.
* [x] Required: User can infinitely scroll through all timelines

Notes:

My biggest pain was solving endless scroll. It wasn't working at first and it was because I had been passing an incorrect maxId into the Timeline API calls. I spent about 12 hours in development and another 12 on figuring out that maxId was the reason I wasn't able to get endless scrolling. Remember kids, breakpoints are your friend. 
 

For future updates:  
- Add retweet
- Add reply
- Add favorites
- Clean up UI

A video of the app via YouTube: https://www.youtube.com/watch?v=VjsMNipt8oY&feature=youtu.be
