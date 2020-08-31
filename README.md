## ValorantAPI

This ValorantAPI that will allow you to access your in-game shop, match history, balance and more. 
More documentation will come out on this and there will also be an example release soon.
## Reporting an issue

If you find an issue you can submit it [here](https://github.com/TewPingz/ValorantAPI/issues).

## Get the Source

1. Install maven `sudo apt-get install maven`
2. Verify installation `mvn -v`
3. Clone the repository `git clone git@github.com:TewPingz/ValorantAPI.git`
4. Navigate to the new folder `cd valorantapi`
5. Import `pom.xml` into your IDE

## Compile a Build

1. Navigate to the repository home directory
2. Run `mvn clean install`
3. Find the compiled jars at `target/ValorantAPI-1.0.jar`

## Usage

1. Compile by following the steps above.
2. Use the following in your desired project.
3.     <dependency>
           <groupId>me.tewpingz.valorant</groupId>
           <artifactId>ValorantAPI</artifactId>
           <version>1.0</version>
           <scope>compile</scope>
       </dependency>

## Contributing

You can submit a [pull request](https://github.com/TewPingz/ValorantAPI/pulls) with your changes.