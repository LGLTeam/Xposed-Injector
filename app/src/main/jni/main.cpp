#include <pthread.h>
#include "KittyMemory/MemoryPatch.h"
#include <android/log.h>
#include <jni.h>
#include <iostream>
#include <fstream>
#include <string>

#define LOG_TAG "JuniorModz"

#define LOGI(...)  __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

 struct My_Patches {

     MemoryPatch ceu_pretro;

 }patches;
bool cp = false;

void *ceu_preto(void *) {
	ProcMap il2cppMap;
	do {
		il2cppMap = KittyMemory::getLibraryMap("libunity.so");
		sleep(1);

	} while(!il2cppMap.isValid());

	if(il2cppMap.isValid()){
	    patches.ceu_pretro = MemoryPatch("libunity.so", 0x33392C, "\xE9\x26\x31\x3E", 4);
	  if (patches.ceu_pretro.Modify()){
            LOGI("ceu preto modificado = TRUE");
	    }
	    else{
            LOGI("ceu preto modificado = FALSE");
	    }

	}

    return NULL;
} //pronto


void *readFile(void *){

        LOGI("readFile");


            while (1){
            FILE *f = NULL;
            char line[200];
            f = fopen("/sdcard/sound_tisk.config", "r");
            while(fgets(line,199,f) != NULL){
                LOGI("line = %s", line);

                if (strstr(line, "cp1") || strstr(line, " cp1")){
                    if (!cp){
                        pthread_t ptid;
                        pthread_create(&ptid, NULL, ceu_preto, NULL);
                        cp = !cp;
                    }
                }

                if(strstr(line, "cp0") || strstr(line, " cp0")){
                    if (cp){
                        if(patches.ceu_pretro.Restore()){
                            LOGI("ceupreto = FALSE");
                            cp= !cp;
                        }
                    }
                }


            }



            sleep(1);
    }
    return NULL;
}


__attribute__((constructor))
void initializer() {
    LOGI("começou");

    FILE *f = NULL;
    f = fopen("/sdcard/sound_tisk.config", "r");
    if (f == NULL)
        fopen("/sdcard/sound_tisk.config", "w+");

    pthread_t ptid;
    pthread_create(&ptid, NULL, readFile, NULL);
}