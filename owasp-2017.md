#Vulnerability Analysis
Het is belangrijk dat het project goed beschermd is tegen aanvallen. Een OWASP-analyse is handig om uit te voeren om in 
kaart t brengen welke kwestsbaarheden het project heeft en hoe deze opgelost kunnen worden.

##A1 Injection
###Description
Onvertrouwbare data-invoer kan als code uitgevoerd worden, wat ook wel injectiekwetsbaarheden genoemd worden.
###Risk
Het kan ervoor zorgen dat ongewenste commands uitgevoerd kunnen worden en zo beschermde data verkregen kunnen worden.
###Counter-measures
In dit project wordt er gebruik gemaakt van JPA en Hibernate. Deze helpen enigzins tegen injectie om zo data veilig
op te kunnen halen wanneer dit mag en geen ongewenste commands uitgevoerd kunnen worden.

##A8 Insecure Deserialization
###Description
Insecure Deserialization houdt in dat er onveilig omgegaan wordt met geserializeerde objecten. 
###Risk
Doordat er onveilig met deze object omgegaan wordt, is er kans dat er onbekende code uitgevoerd wordt wat ongewenst 
gedrag kan opleveren of zelfs data kan aanpassen.
###Counter-measures
In dit project is het niet nodig om geserializeerde objecten mee te geven. De data is opgeslagen aan de hand van 
tabellen in een PostGreSQL database. Wanneer een geserializeed object wordt meegegeven aan het project, zal dit niet 
overkomen met de gevraagde informatie en zal er een error gegooid worden.

##A9 Using dependencies with knowing vulnerabilities
###Description
Er worden dependencies gebruikt die bekende kwetsbaarheden hebben. Dit kan komen doordat er een oudere versie gebruikt 
wordt.
###Risk
Door deze kwetsbare dependencies is het mogelijk dat aangevallers via deze kwetsbaarheden een aanval kunnen plegen. 
Dit kan leiden tot dataverlies of server overname.
###Counter-measures
In dit project wordt er in de pom.xml duidelijk aangegeven welke versies er gebruikt worden van dependencies. 
Daarbij wordt er ook gebruik gemaakt van Dependabot. Deze kijkt welke dependencies een nieuwe versie heeft en zal deze 
ook updaten wanneer dit kan. Hierdoor blijven de dependencies up-to-date.