/******************************************************************************
 * File Name   : MX_Device.h
 * Date        : 27/03/2019 22:55:10
 * Description : STM32Cube MX parameter definitions
 * Note        : This file is generated by STM32CubeMX (DO NOT EDIT!)
 ******************************************************************************/

#ifndef __MX_DEVICE_H
#define __MX_DEVICE_H

/*---------------------------- Clock Configuration ---------------------------*/

#define MX_LSI_VALUE                            32000
#define MX_LSE_VALUE                            32768
#define MX_HSI_VALUE                            16000000
#define MX_HSE_VALUE                            8000000
#define MX_EXTERNAL_CLOCK_VALUE                 12288000
#define MX_PLLCLKFreq_Value                     72000000
#define MX_SYSCLKFreq_VALUE                     16000000
#define MX_HCLKFreq_Value                       16000000
#define MX_FCLKCortexFreq_Value                 16000000
#define MX_CortexFreq_Value                     16000000
#define MX_AHBFreq_Value                        16000000
#define MX_APB1Freq_Value                       16000000
#define MX_APB2Freq_Value                       16000000
#define MX_APB1TimFreq_Value                    16000000
#define MX_APB2TimFreq_Value                    16000000
#define MX_48MHZClocksFreq_Value                48000000
#define MX_EthernetFreq_Value                   16000000
#define MX_I2SClocksFreq_Value                  192000000
#define MX_RTCFreq_Value                        32000
#define MX_WatchDogFreq_Value                   32000
#define MX_MCO1PinFreq_Value                    16000000
#define MX_MCO2PinFreq_Value                    16000000

/*-------------------------------- ADC1       --------------------------------*/

#define MX_ADC1                                 1

/* GPIO Configuration */

/* Pin PA0-WKUP */
#define MX_ADCx_IN0_Pin                         PA0_WKUP
#define MX_ADCx_IN0_GPIOx                       GPIOA
#define MX_ADCx_IN0_GPIO_PuPd                   GPIO_NOPULL
#define MX_ADCx_IN0_GPIO_Pin                    GPIO_PIN_0
#define MX_ADCx_IN0_GPIO_Mode                   GPIO_MODE_ANALOG

/* Pin PA1 */
#define MX_ADCx_IN1_Pin                         PA1
#define MX_ADCx_IN1_GPIOx                       GPIOA
#define MX_ADCx_IN1_GPIO_PuPd                   GPIO_NOPULL
#define MX_ADCx_IN1_GPIO_Pin                    GPIO_PIN_1
#define MX_ADCx_IN1_GPIO_Mode                   GPIO_MODE_ANALOG

/* DMA Configuration */

/* DMA ADC1 */
#define MX_ADC1_DMA_DMA_Handle                  
#define MX_ADC1_DMA_Instance                    DMA2_Stream0
#define MX_ADC1_DMA_FIFOMode                    DMA_FIFOMODE_DISABLE
#define MX_ADC1_DMA_Priority                    DMA_PRIORITY_MEDIUM
#define MX_ADC1_DMA_Channel                     DMA_CHANNEL_0
#define MX_ADC1_DMA_PeriphDataAlignment         DMA_PDATAALIGN_WORD
#define MX_ADC1_DMA_MemDataAlignment            DMA_MDATAALIGN_WORD
#define MX_ADC1_DMA_Mode                        DMA_CIRCULAR
#define MX_ADC1_DMA_Direction                   DMA_PERIPH_TO_MEMORY
#define MX_ADC1_DMA_PeriphInc                   DMA_PINC_DISABLE
#define MX_ADC1_DMA_MemInc                      DMA_MINC_ENABLE
#define MX_ADC1_DMA_IpInstance                  

/*-------------------------------- ADC2       --------------------------------*/

#define MX_ADC2                                 1

/* GPIO Configuration */

/* DMA Configuration */

/* DMA ADC2 */
#define MX_ADC2_DMA_DMA_Handle                  
#define MX_ADC2_DMA_Instance                    DMA2_Stream2
#define MX_ADC2_DMA_FIFOMode                    DMA_FIFOMODE_DISABLE
#define MX_ADC2_DMA_Priority                    DMA_PRIORITY_MEDIUM
#define MX_ADC2_DMA_Channel                     DMA_CHANNEL_1
#define MX_ADC2_DMA_PeriphDataAlignment         DMA_PDATAALIGN_HALFWORD
#define MX_ADC2_DMA_MemDataAlignment            DMA_MDATAALIGN_HALFWORD
#define MX_ADC2_DMA_Mode                        DMA_NORMAL
#define MX_ADC2_DMA_Direction                   DMA_PERIPH_TO_MEMORY
#define MX_ADC2_DMA_PeriphInc                   DMA_PINC_DISABLE
#define MX_ADC2_DMA_MemInc                      DMA_MINC_DISABLE
#define MX_ADC2_DMA_IpInstance                  

/*-------------------------------- DMA        --------------------------------*/

#define MX_DMA                                  1

/* NVIC Configuration */

/* NVIC DMA2_Stream2_IRQn */
#define MX_DMA2_Stream2_IRQn_interruptPremptionPriority 0
#define MX_DMA2_Stream2_IRQn_PriorityGroup      NVIC_PRIORITYGROUP_4
#define MX_DMA2_Stream2_IRQn_Subriority         0

/* NVIC DMA2_Stream0_IRQn */
#define MX_DMA2_Stream0_IRQn_interruptPremptionPriority 0
#define MX_DMA2_Stream0_IRQn_PriorityGroup      NVIC_PRIORITYGROUP_4
#define MX_DMA2_Stream0_IRQn_Subriority         0

/*-------------------------------- SYS        --------------------------------*/

#define MX_SYS                                  1

/* GPIO Configuration */

/*-------------------------------- USB_DEVICE --------------------------------*/

#define MX_USB_DEVICE                           1

/* GPIO Configuration */

/*-------------------------------- NVIC       --------------------------------*/

#define MX_NVIC                                 1

/*-------------------------------- GPIO       --------------------------------*/

#define MX_GPIO                                 1

/* GPIO Configuration */

/* Pin PD12 */
#define MX_PD12_GPIO_Speed                      GPIO_SPEED_FREQ_LOW
#define MX_PD12_Pin                             PD12
#define MX_PD12_GPIOx                           GPIOD
#define MX_PD12_PinState                        GPIO_PIN_RESET
#define MX_PD12_GPIO_PuPd                       GPIO_NOPULL
#define MX_PD12_GPIO_Pin                        GPIO_PIN_12
#define MX_PD12_GPIO_ModeDefaultOutputPP        GPIO_MODE_OUTPUT_PP

/* Pin PD14 */
#define MX_PD14_GPIO_Speed                      GPIO_SPEED_FREQ_LOW
#define MX_PD14_Pin                             PD14
#define MX_PD14_GPIOx                           GPIOD
#define MX_PD14_PinState                        GPIO_PIN_RESET
#define MX_PD14_GPIO_PuPd                       GPIO_NOPULL
#define MX_PD14_GPIO_Pin                        GPIO_PIN_14
#define MX_PD14_GPIO_ModeDefaultOutputPP        GPIO_MODE_OUTPUT_PP

/* Pin PD13 */
#define MX_PD13_GPIO_Speed                      GPIO_SPEED_FREQ_LOW
#define MX_PD13_Pin                             PD13
#define MX_PD13_GPIOx                           GPIOD
#define MX_PD13_PinState                        GPIO_PIN_RESET
#define MX_PD13_GPIO_PuPd                       GPIO_NOPULL
#define MX_PD13_GPIO_Pin                        GPIO_PIN_13
#define MX_PD13_GPIO_ModeDefaultOutputPP        GPIO_MODE_OUTPUT_PP

/* Pin PD15 */
#define MX_PD15_GPIO_Speed                      GPIO_SPEED_FREQ_LOW
#define MX_PD15_Pin                             PD15
#define MX_PD15_GPIOx                           GPIOD
#define MX_PD15_PinState                        GPIO_PIN_RESET
#define MX_PD15_GPIO_PuPd                       GPIO_NOPULL
#define MX_PD15_GPIO_Pin                        GPIO_PIN_15
#define MX_PD15_GPIO_ModeDefaultOutputPP        GPIO_MODE_OUTPUT_PP

#endif  /* __MX_DEVICE_H */

