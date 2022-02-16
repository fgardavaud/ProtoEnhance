# ProtoEnhance

[![Licence](https://img.shields.io/badge/license-GNU__GPLv3-yellow)](http://choosealicense.com/licenses/gpl-3.0/)
[![ORCiD](https://img.shields.io/badge/ORCID-0000--0001--9767--3241-green)](http://orcid.org/0000-0001-9767-3241) 


## General purpose
To optimize and follow-up CT protocol acquisition settings for GE machines.
New GE Revolution CT scanners (with new GUI) are not currently supported.



## Software language

**This software is only available in French for the moment**. I encourage someone to translate this soft in other languages. If needed, I could possibly translate this soft in English. 

## CT system requirement
This code has been tested on VCT, 750HD and RevoGSI systems. But, I expect all GE CT units with protocol file system with VersionName = "3.5.1" will work with ProtoEnhance.
A CT system state save has been included in this repo to have a "user" protocol database for testing purpose. service_mod_data.7z file have to be decompressed (see CT images sample section to have clue for decompress tool).
You have to create your own system state to operate ProtoEnhance on your own CT protocols.

## OS platform

This code has been developed in Java language on Windows Seven platform (more precisely in Eclipse IDE).
**But**, all the functions are normally other OS platform proof. 

The code has been exhaustively commented, as you can understand the function of every code block. 

## Software configuration

It extremely recommended to follow the software procedure included in this repo. First you have to decompress the CT images database in DICOM format to operate the software (see the section below).


## CT images sample

A compressed CT images sample folder for brain localization have been included in this repo for testing purpose.  To do so, you have to decompress the 7-zip archive with additional software such as [7-zip](https://www.7-zip.org) on Windows or [Keka](https://www.keka.io/en/) on macOS.

The whole CT images database is available on the [French society of medical physics software database](http://www.sfpm.asso.fr/download/index.php?act=download&id=208)

-----

Have a nice utilization and contribution.

## Authors

François Gardavaud, Hugo Pasquier for software designing and developpement and Pr Alain Luciani, Pr Alain Rahmouni for image quality medical validation
