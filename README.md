# RangeCalender
Library to show Calender which takes from Date and To date. Useful while generating reports,book tickets etc.

Steps to integrate:

allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
	        }
	}
  
  dependencies {
  	        implementation 'com.github.PalashDumbare:RangeCalender:Tag'
 	}
  
   new RangeCalender(this, new OnDateSelected()  
  	  @Override
            public void dateSelectedIs(Date fromDate, Date toDate) {
            }
        }).show();

![Image of Yaktocat](https://github.com/PalashDumbare/RangeCalender/blob/master/device-2019-07-18-164401.png)
