# Axon Server Plugin - Example/edited by PawelCZ1

## How it works
This plugin is written on top of the [axon-server-plugin-api](https://github.com/AxonIQ/axon-server-plugin-api) using two of the provided _Interceptors_.
1. `AppendEventInterceptor` manipulating the event on the way in, adding some piece of information to the metadata of the event;
1. `ReadEventInterceptor` intercepting the event on the way back, trying to read that piece of information from the metadata.

On both operations you should get the event logged as well as an additional message when the metadata of the event contains the `plugin` field we are looking for.

## How to build
_Axon Server Plugins_ are _OSGi_ modules that contain the plugin implementations. It's written with _Java_ and _Maven_. Because of that, a simple `mvn clean verify` is enough to build a jar into the `target` folder that can be uploaded into _Axon Server Plugin Interface_.

Uploading, activating, pausing and deleting the plugin can be automated by the `axonserver-cli`.
For more info, check [here](https://docs.axoniq.io/reference-guide/axon-server/administration/admin-configuration/command-line-interface#plugins).

## How to run
For testing purposes, a `docker-compose.yml` file with _Axon Server_ is provided and configured. To use it, running `docker compose up` in the same directory of the file is enough to have the needed infrastructure pieces up and running. After building or downloading the plugin, you have to upload it on _Axon Server Plugin Interface_. Once it is uploaded, you have to _Start_ the plugin by clicking on the start button.

---

Created with :heart: by [AxonIQ](https://axoniq.io/)


# Additional functions 
`AppendEventInterceptor` sends every Investment aggregate event to the given zapier url and for the every event puts an aggregate type into the metadata

## How to set up
    1. Execute mvn clean verify
    2. Run the Axon Server via docker-compose.yml
    3. Open the Axon Server Dashboard through a browser and go to the Plugins category
    4. Upload .jar file located at /target
    5. Make sure plugin is started
