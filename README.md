# ReviewPushAndroidFeedback

ReviewPushAndroidFeedback is the android library for ReviewPush. You can use this to request feedback for your business or app in an android application.

## Getting Started

ReviewPushAndroidFeedback can be downloaded from:

```
    compile 'com.broadwaylab.reviewpushframework:reviewpushframework:1.0'
```

The FeedbackDialog is based on the DialogFragment class, it is compatible with Android SDK 16 and superior

### Video

Watch the full video <a href="https://vimeo.com/247637937" target="_blank">here</a>.

<a href="https://vimeo.com/247637937" target="_blank"><img src="https://media.giphy.com/media/l4EpbX4shoUxdAGIw/giphy.gif" 
alt="Example" width="375" height="667" border="10" /></a>

### Usage

ReviewPush Android is ready to be used, just add your information and call the Dialog.
To use this SDK you will need both an API key and secret. These credentials can be found on your  [ReviewPush Dashboard](http://dashboard.reviewpush.com/) under Settings -> ReviewPush API.

Note: This information is only available to accounts with admin rights.

Example code:
```
String key = getResources().getString(R.string.review_push_key);
String locationId = getResources().getString(R.string.review_push_secret);
String secret = getResources().getString(R.string.review_push_location_id);
//set auth values to the auth
FeedbackAuth auth= new FeedbackAuth(key, secret, locationId,"Name","email");
FeedbackDialog mDialog = new FeedbackDialog(); 
FeedbackConfiguration configuration = new FeedbackConfiguration(auth);
configuration.setType(FeedbackType.GENERAL);
// set configuration and add the Dialog to the fragment manager
mDialog.setConfiguration(configuration);
mDialog.show(getFragmentManager(), "FeedbackDialog");
```
## Example APP

The example app can be found on example folder, import the project and replace the String values and run the project.


##### Configuration

The Library includes some configurations that you will find on the Configuration class

| Property  | Description |
| --------- | ------------- |
| confettiEnabled | Boolean, this value will enable or disable the confetti on the Dialog | 
| positiveColor | Int, value of the positive button background | 
| negativeColor | int, Value of the negative button background | 
| Type | Enum, General or AppFeedback | 
| ButtonYesText | String, Text for the Yes Button | 
| ButtonNoText | String , Text for the NO button | 
| title | String , Text for the initial title | 
| titleAfterReviewNegative | String , if(rate>=3) text for the first title after the rate view changes | 
| titleAfterReviewPositive | String , if(rate>=3) text for the first title after the rate view changes | 
| sitesDescription | String , DescriptionText to introduce the user the sites review |
| titleNegativeFeedback | String , Title for the negative feedback (<=3) |
| titlePositiveFeedback | String , Title for the positive feedback >3 |


Example of customization
```
FeedbackConfiguration configuration = new FeedbackConfiguration(auth);
            configuration.setConfettiEnabled(true);
            configuration.setPositiveColor(Color.RED);
            configuration.setNegativeColor(Color.BLACK);
            configuration.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_launcher_background));
            configuration.setType(FeedbackType.GENERAL);
```

### And custom Copy

Example of custom copy

```
configuration.setButtonNoText("Nope");
            configuration.setButtonYesText("Yes!");
            configuration.setTitle("Could you rate your experience here?");
            configuration.setTitleAfterReviewNegative("We're bummed you had a bad time. Can you tell us why?");
            configuration.setSitesDescription("Your opinion is very important");
            configuration.setTitleNegativeFeedback("Tell us, how can we do it better?");
            configuration.setTitlePositiveFeedback("We are happy to have you here!, Rate us on other sites");
```

## Custom star Color

If you want to change the color of the star add to your color resource files the next value, with your custom Color:

```
    <color name="starActivated">#1fa3ac</color>
    <color name="starNormal">#434343</color>
```
## Built With

* [OkHttp](http://square.github.io/okhttp/) - Http Library
* [Confetti](https://github.com/jinatonic/confetti) - Confetti resource library

## Contributing

Please feel free to submit your request if you think we should improve something

## Author

* **Rodolfo Abarca** - *Version 1* - [r9software](https://github.com/r9software)

See also the list of [contributors](https://github.com/broadwaylab/ReviewPushAndroidFeedback/contributors) who participated in this project.

## License

This project is licensed under the MIT License
