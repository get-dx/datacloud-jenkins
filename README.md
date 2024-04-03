# dx-data-sharer

## Introduction

This plugin is intended for customers of DX (https://getdx.com/). It shares Jenkins build data with DX.

To run this plugin locally:
- Install Jenkins: https://www.jenkins.io/doc/pipeline/tour/getting-started/
- Setup your developer environment: https://www.jenkins.io/doc/developer/tutorial/prepare/
- `mvn hpi:run -Dport=5000`
- Navigate to http://localhost:5000/jenkins

To generate a *.hpi file:
- `mvn package`

## Getting started
### Installing the Plugin
- Download or generate the plugin (*.hpi file) and put it in the following directory: <jenkinsHome>/plugins/
- restart Jenkins

### Adding Your DX API Key to Jenkins
- Click “Manage Jenkins” in the menu on the left side of the homepage.
<img width="322" alt="Screenshot 2024-04-01 at 12 16 19 PM" src="https://github.com/mattilavan/jenkins-plugins/assets/44679211/670767a6-6acc-4da0-9596-e32b7e8dbcc5">

- Click “Manage Credentials” in the Security section
<img width="969" alt="Screenshot 2024-04-01 at 12 16 40 PM" src="https://github.com/mattilavan/jenkins-plugins/assets/44679211/ab07cd71-d6aa-4a55-87c8-349c196b538d">

- You will see your credentials, if you have any. Click on the store or domain you would like to add your DX API Key to.
<img width="952" alt="Screenshot 2024-04-01 at 12 23 43 PM" src="https://github.com/mattilavan/jenkins-plugins/assets/44679211/185658c3-feb7-4a33-8712-7bca499cf013">

- Click “Add Credentials”.
<img width="343" alt="Screenshot 2024-04-01 at 12 25 13 PM" src="https://github.com/mattilavan/jenkins-plugins/assets/44679211/3b2be1f0-e341-4de3-bf65-0947408cce73">

- Choose “Secret Text” as “Kind”
- Choose your desired scope
- Add your Key as the “Secret”
- Use the name “dx_token” as “ID”
- Use “An API Key for the DX API” as “Description”
- Click “OK”

### Adding Your DX URL to Jenkins
- Follow the same steps you used to add your API Key, but use the following values:
-- ID: dx_path
-- Secret: your company’s DX URL
-- Description: Company-specific DX URL



TODO Tell users how to configure your plugin here, include screenshots, pipeline examples and
configuration-as-code examples.

## Issues

Report issues and enhancements in the [Jenkins issue tracker](https://issues.jenkins.io/).

## Contributing

[CONTRIBUTING](https://github.com/jenkinsci/.github/blob/master/CONTRIBUTING.md)

Refer to our [contribution guidelines](https://github.com/jenkinsci/.github/blob/master/CONTRIBUTING.md)

## LICENSE

Licensed under MIT, see [LICENSE](LICENSE.md)

