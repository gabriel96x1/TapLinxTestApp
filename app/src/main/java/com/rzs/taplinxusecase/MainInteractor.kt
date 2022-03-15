package com.code.taplinxtestapp

class MainInteractor {

    private var m_strKey = "9f40751e07e79ef57fa37c2bef019f73"
    private lateinit var mLibInstance: nxpLib
    private val DEFAULT_PICC_MASTER = byteArrayOf(0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00)
    lateinit var keyDefault: Key
    lateinit var keyDataDefault: KeyData


    private fun initializeLibrary(){
        print("abrInicio")
        mLibInstance = nxpLib.getInstance()
        mLibInstance.registerActivity(this, m_strKey)
        Log.d("D/TPLINX", "message")

    }
    private fun initializeCipherinitVector() {
        /* Initialize the Cipher */
        try {
            var cipher = Cipher.getInstance("AES/CBC/NoPadding")
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: NoSuchPaddingException) {
            e.printStackTrace()
        }
        /* set Application Master Key */var bytesKey = DEFAULT_PICC_MASTER

        /* Initialize init vector of 16 bytes with 0xCD. It could be anything */
        val ivSpec = ByteArray(16)
        Arrays.fill(ivSpec, 0xCD.toByte())
        var iv = IvParameterSpec(ivSpec)
    }

    private fun cardLogic(): ByteArray {
        print("abrInicio")

        initializeLibrary()

        initializeCipherinitVector()

        var DESFire1: desfire = DESFireFactory.getInstance().getDESFire(
            mLibInstance.customModules
        )

        DESFire1.reader.connect()

        Log.d("D/TPLINX", "message")
        Log.d("D/TPLINX","TapLinx version: " + nxpLib.getTaplinxVersion())
        Log.d("D/TPLINX","Card Details: " + DESFire1.cardDetails.cardName)
        Log.d("D/TPLINX","Free Memory: " + DESFire1.freeMemory)
        Log.d("D/TPLINX","Total Memory: " + DESFire1.totalMemory)

        keyDefault = SecretKeySpec(DEFAULT_PICC_MASTER, "3DES")
        keyDataDefault.key

        DESFire1.selectApplication(1)
        DESFire1.authenticate(1, desfire.AuthType.Native, KeyType.THREEDES, keyDataDefault)

        var data = DESFire1.readData(9, 0, 16, desfire.CommunicationType.Plain, 16)

        print(data)

        return data


        /*catch (e:Exception) {
            print(e)
            return byteArrayOf(0x00)
        }*/
    }



}