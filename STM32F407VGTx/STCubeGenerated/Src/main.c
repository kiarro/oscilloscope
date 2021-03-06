/* USER CODE BEGIN Header */
/**
  ******************************************************************************
  * @file           : main.c
  * @brief          : Main program body
  ******************************************************************************
  * @attention
  *
  * <h2><center>&copy; Copyright (c) 2019 STMicroelectronics.
  * All rights reserved.</center></h2>
  *
  * This software component is licensed by ST under Ultimate Liberty license
  * SLA0044, the "License"; You may not use this file except in compliance with
  * the License. You may obtain a copy of the License at:
  *                             www.st.com/SLA0044
  *
  ******************************************************************************
  */
/* USER CODE END Header */

/* Includes ------------------------------------------------------------------*/
#include "main.h"
#include <stdio.h>
#include "usb_device.h"

/* Private includes ----------------------------------------------------------*/
/* USER CODE BEGIN Includes */

/* USER CODE END Includes */

/* Private typedef -----------------------------------------------------------*/
/* USER CODE BEGIN PTD */

/* USER CODE END PTD */

/* Private define ------------------------------------------------------------*/
/* USER CODE BEGIN PD */

/* USER CODE END PD */

/* Private macro -------------------------------------------------------------*/
/* USER CODE BEGIN PM */

/* USER CODE END PM */

/* Private variables ---------------------------------------------------------*/
ADC_HandleTypeDef hadc1;
ADC_HandleTypeDef hadc2;
DMA_HandleTypeDef hdma_adc1;
DMA_HandleTypeDef hdma_adc2;

/* USER CODE BEGIN PV */
uint32_t adc1Value[2];
uint32_t adc2Value[2];
uint32_t adcValueForSend[3];

char str_tx[30];
//uint32_t adc1Value_16[2];
/* USER CODE END PV */

/* Private function prototypes -----------------------------------------------*/
void SystemClock_Config(void);
static void MX_GPIO_Init(void);
static void MX_DMA_Init(void);
static void MX_ADC1_Init(void);
static void MX_ADC2_Init(void);
/* USER CODE BEGIN PFP */
void Delay(uint32_t Val);
void changeChannel(uint32_t ch, uint32_t pin);
uint8_t CDC_Transmit_FS(uint8_t* Buf, uint16_t Len);
void changeCoupling(uint32_t pin, uint32_t coup);
void changeProbe(uint32_t pin, uint32_t probe);
void applySettings(uint8_t* settings);
/* USER CODE END PFP */

/* Private user code ---------------------------------------------------------*/
/* USER CODE BEGIN 0 */
#define ITM_Port8(n) (*((volatile unsigned char *)(0xE0000000+4*n)))
#define ITM_Port16(n) (*((volatile unsigned short *)(0xE0000000+4*n)))
#define ITM_Port32(n) (*((volatile unsigned long *)(0xE0000000+4*n)))
	
#define DEMCR (*((volatile unsigned long *)(0xE000EDFC)))
#define TRCENA 0x01000000

struct __FILE{int handle;};
FILE __stdout;
FILE __stdin;

int fputc(int ch, FILE *f){
	if (DEMCR & TRCENA){
		while (ITM_Port32(0) == 0);
		ITM_Port8(0) = ch;
	}
	return(ch);
}
/* USER CODE END 0 */

/**
  * @brief  The application entry point.
  * @retval int
  */
int main(void)
{
  /* USER CODE BEGIN 1 */

  /* USER CODE END 1 */
  

  /* MCU Configuration--------------------------------------------------------*/

  /* Reset of all peripherals, Initializes the Flash interface and the Systick. */
  HAL_Init();

  /* USER CODE BEGIN Init */

  /* USER CODE END Init */

  /* Configure the system clock */
  SystemClock_Config();

  /* USER CODE BEGIN SysInit */

  /* USER CODE END SysInit */

  /* Initialize all configured peripherals */
  MX_GPIO_Init();
  MX_DMA_Init();
  MX_ADC1_Init();
  MX_ADC2_Init();
  MX_USB_DEVICE_Init();
  /* USER CODE BEGIN 2 */
	char h[4] = "0000";
//	HAL_ADC_Start_DMA(&hadc1, adc1Value, 2);
	printf("0000-%d\n\r", h[0]);
  uint8_t is_transmission_on = 0;
  /* USER CODE END 2 */

  /* Infinite loop */
  /* USER CODE BEGIN WHILE */
	changeChannel(0, 8);
	changeChannel(1, 9);
	changeChannel(2, 10);
	changeChannel(3, 11);
  while (1)
  {
		//HAL_GPIO_WritePin(GPIOD, GPIO_PIN_14, GPIO_PIN_SET);
		
		//MX_USB_DEVICE_Init();
//		while(1)
//		{
		//HAL_GPIO_WritePin(GPIOD, GPIO_PIN_12, GPIO_PIN_SET);
		//HAL_Delay(100);
		//HAL_GPIO_WritePin(GPIOD, GPIO_PIN_12, GPIO_PIN_RESET);
		//HAL_Delay(100);
//		};
//		if (is_transmission_on == 0)
//		{
//			continue;
//		}
		HAL_Delay(1);

//		adc1Value_16[0]=adc1Value[0];
//		adc1Value_16[1]=adc1Value[1];		
		HAL_ADC_Start_DMA(&hadc1, adc1Value, 2);
		HAL_ADC_ConvCpltCallback(&hadc1);
		HAL_ADC_ConvCpltCallback(&hadc1);
		HAL_ADC_Stop_DMA(&hadc1);
		HAL_ADC_Start_DMA(&hadc2, adc2Value, 2);
		HAL_ADC_ConvCpltCallback(&hadc2);
		HAL_ADC_ConvCpltCallback(&hadc2);
		HAL_ADC_Stop_DMA(&hadc2);
		
//		if (adc1Value[0] > adc2Value[1]){
//			adc2Value[0] = adc1Value[0] - adc2Value[1];
//		}
//		else{
//			adc2Value[0] = adc2Value[1] - adc2Value[0];
//			adc2Value[0] += 32768;
//		}
		
		adcValueForSend[0] = adc1Value[0];
		adcValueForSend[1] = adc2Value[0];
		adcValueForSend[0] += adc1Value[1]*65536;
		adcValueForSend[1] += adc2Value[1]*65536;
		//adcValueForSend[2] = 1;
		
//		CDC_Transmit_FS((uint8_t*)str_tx, strlen(str_tx));
		CDC_Transmit_FS((uint8_t*)adcValueForSend, 8);
//		if (adc1Value[0]<0x333){
//			GPIOD->ODR = 0x0000;
//		} else if (adc1Value[1]<0x666){
//			GPIOD->ODR = 0x1000;
//		} else if (adc1Value[1]<0x999){
//			GPIOD->ODR = 0x3000;
//		} else if (adc1Value[1]<0xCCC){
//			GPIOD->ODR = 0x7000;
//		} else GPIOD->ODR = 0xf000;
		
    /* USER CODE END WHILE */

    /* USER CODE BEGIN 3 */
  }
	int a = 0;
  /* USER CODE END 3 */
}

/**
  * @brief System Clock Configuration
  * @retval None
  */
void SystemClock_Config(void)
{
  RCC_OscInitTypeDef RCC_OscInitStruct = {0};
  RCC_ClkInitTypeDef RCC_ClkInitStruct = {0};

  /** Configure the main internal regulator output voltage 
  */
  __HAL_RCC_PWR_CLK_ENABLE();
  __HAL_PWR_VOLTAGESCALING_CONFIG(PWR_REGULATOR_VOLTAGE_SCALE1);
  /** Initializes the CPU, AHB and APB busses clocks 
  */
  RCC_OscInitStruct.OscillatorType = RCC_OSCILLATORTYPE_HSI|RCC_OSCILLATORTYPE_HSE;
  RCC_OscInitStruct.HSEState = RCC_HSE_ON;
  RCC_OscInitStruct.HSIState = RCC_HSI_ON;
  RCC_OscInitStruct.HSICalibrationValue = RCC_HSICALIBRATION_DEFAULT;
  RCC_OscInitStruct.PLL.PLLState = RCC_PLL_ON;
  RCC_OscInitStruct.PLL.PLLSource = RCC_PLLSOURCE_HSE;
  RCC_OscInitStruct.PLL.PLLM = 4;
  RCC_OscInitStruct.PLL.PLLN = 72;
  RCC_OscInitStruct.PLL.PLLP = RCC_PLLP_DIV2;
  RCC_OscInitStruct.PLL.PLLQ = 3;
  if (HAL_RCC_OscConfig(&RCC_OscInitStruct) != HAL_OK)
  {
    Error_Handler();
  }
  /** Initializes the CPU, AHB and APB busses clocks 
  */
  RCC_ClkInitStruct.ClockType = RCC_CLOCKTYPE_HCLK|RCC_CLOCKTYPE_SYSCLK
                              |RCC_CLOCKTYPE_PCLK1|RCC_CLOCKTYPE_PCLK2;
  RCC_ClkInitStruct.SYSCLKSource = RCC_SYSCLKSOURCE_HSI;
  RCC_ClkInitStruct.AHBCLKDivider = RCC_SYSCLK_DIV1;
  RCC_ClkInitStruct.APB1CLKDivider = RCC_HCLK_DIV1;
  RCC_ClkInitStruct.APB2CLKDivider = RCC_HCLK_DIV1;

  if (HAL_RCC_ClockConfig(&RCC_ClkInitStruct, FLASH_LATENCY_0) != HAL_OK)
  {
    Error_Handler();
  }
}

/**
  * @brief ADC1 Initialization Function
  * @param None
  * @retval None
  */
static void MX_ADC1_Init(void)
{

  /* USER CODE BEGIN ADC1_Init 0 */

  /* USER CODE END ADC1_Init 0 */

  ADC_ChannelConfTypeDef sConfig = {0};

  /* USER CODE BEGIN ADC1_Init 1 */

  /* USER CODE END ADC1_Init 1 */
  /** Configure the global features of the ADC (Clock, Resolution, Data Alignment and number of conversion) 
  */
  hadc1.Instance = ADC1;
  hadc1.Init.ClockPrescaler = ADC_CLOCK_SYNC_PCLK_DIV2;
  hadc1.Init.Resolution = ADC_RESOLUTION_12B;
  hadc1.Init.ScanConvMode = ENABLE;
  hadc1.Init.ContinuousConvMode = DISABLE;
  hadc1.Init.DiscontinuousConvMode = DISABLE;
  hadc1.Init.ExternalTrigConvEdge = ADC_EXTERNALTRIGCONVEDGE_NONE;
  hadc1.Init.ExternalTrigConv = ADC_SOFTWARE_START;
  hadc1.Init.DataAlign = ADC_DATAALIGN_RIGHT;
  hadc1.Init.NbrOfConversion = 2;
  hadc1.Init.DMAContinuousRequests = DISABLE;
  hadc1.Init.EOCSelection = ADC_EOC_SINGLE_CONV;
  if (HAL_ADC_Init(&hadc1) != HAL_OK)
  {
    Error_Handler();
  }
  /** Configure for the selected ADC regular channel its corresponding rank in the sequencer and its sample time. 
  */
  sConfig.Channel = ADC_CHANNEL_0;
  sConfig.Rank = 1;
  sConfig.SamplingTime = ADC_SAMPLETIME_3CYCLES;
  if (HAL_ADC_ConfigChannel(&hadc1, &sConfig) != HAL_OK)
  {
    Error_Handler();
  }
  /** Configure for the selected ADC regular channel its corresponding rank in the sequencer and its sample time. 
  */
  sConfig.Channel = ADC_CHANNEL_3;
  sConfig.Rank = 2;
  if (HAL_ADC_ConfigChannel(&hadc1, &sConfig) != HAL_OK)
  {
    Error_Handler();
  }
  /* USER CODE BEGIN ADC1_Init 2 */

  /* USER CODE END ADC1_Init 2 */

}

/**
  * @brief ADC2 Initialization Function
  * @param None
  * @retval None
  */
static void MX_ADC2_Init(void)
{

  /* USER CODE BEGIN ADC2_Init 0 */

  /* USER CODE END ADC2_Init 0 */

  ADC_ChannelConfTypeDef sConfig = {0};

  /* USER CODE BEGIN ADC2_Init 1 */

  /* USER CODE END ADC2_Init 1 */
  /** Configure the global features of the ADC (Clock, Resolution, Data Alignment and number of conversion) 
  */
  hadc2.Instance = ADC2;
  hadc2.Init.ClockPrescaler = ADC_CLOCK_SYNC_PCLK_DIV2;
  hadc2.Init.Resolution = ADC_RESOLUTION_12B;
  hadc2.Init.ScanConvMode = ENABLE;
  hadc2.Init.ContinuousConvMode = DISABLE;
  hadc2.Init.DiscontinuousConvMode = DISABLE;
  hadc2.Init.ExternalTrigConvEdge = ADC_EXTERNALTRIGCONVEDGE_NONE;
  hadc2.Init.ExternalTrigConv = ADC_SOFTWARE_START;
  hadc2.Init.DataAlign = ADC_DATAALIGN_RIGHT;
  hadc2.Init.NbrOfConversion = 2;
  hadc2.Init.DMAContinuousRequests = DISABLE;
  hadc2.Init.EOCSelection = ADC_EOC_SINGLE_CONV;
  if (HAL_ADC_Init(&hadc2) != HAL_OK)
  {
    Error_Handler();
  }
  /** Configure for the selected ADC regular channel its corresponding rank in the sequencer and its sample time. 
  */
  sConfig.Channel = ADC_CHANNEL_7;
  sConfig.Rank = 1;
  sConfig.SamplingTime = ADC_SAMPLETIME_3CYCLES;
  if (HAL_ADC_ConfigChannel(&hadc2, &sConfig) != HAL_OK)
  {
    Error_Handler();
  }
  /* USER CODE BEGIN ADC2_Init 2 */
	sConfig.Channel = ADC_CHANNEL_11;
  sConfig.Rank = 2;
  if (HAL_ADC_ConfigChannel(&hadc2, &sConfig) != HAL_OK)
  {
    Error_Handler();
  }
  /* USER CODE END ADC2_Init 2 */

}

/** 
  * Enable DMA controller clock
  */
static void MX_DMA_Init(void) 
{
  /* DMA controller clock enable */
  __HAL_RCC_DMA2_CLK_ENABLE();

  /* DMA interrupt init */
  /* DMA2_Stream0_IRQn interrupt configuration */
  HAL_NVIC_SetPriority(DMA2_Stream0_IRQn, 0, 0);
  HAL_NVIC_EnableIRQ(DMA2_Stream0_IRQn);
  /* DMA2_Stream2_IRQn interrupt configuration */
  HAL_NVIC_SetPriority(DMA2_Stream2_IRQn, 0, 0);
  HAL_NVIC_EnableIRQ(DMA2_Stream2_IRQn);

}

/**
  * @brief GPIO Initialization Function
  * @param None
  * @retval None
  */
static void MX_GPIO_Init(void)
{
  GPIO_InitTypeDef GPIO_InitStruct = {0};

  /* GPIO Ports Clock Enable */
  __HAL_RCC_GPIOE_CLK_ENABLE();
  __HAL_RCC_GPIOC_CLK_ENABLE();
  __HAL_RCC_GPIOH_CLK_ENABLE();
  __HAL_RCC_GPIOA_CLK_ENABLE();
  __HAL_RCC_GPIOB_CLK_ENABLE();
  __HAL_RCC_GPIOD_CLK_ENABLE();

  /*Configure GPIO pin Output Level */
  HAL_GPIO_WritePin(GPIOE, GPIO_PIN_4|GPIO_PIN_5|GPIO_PIN_6|GPIO_PIN_7 
                          |GPIO_PIN_8|GPIO_PIN_9|GPIO_PIN_10|GPIO_PIN_11 
                          |GPIO_PIN_12|GPIO_PIN_13|GPIO_PIN_14|GPIO_PIN_15, GPIO_PIN_RESET);

  /*Configure GPIO pin Output Level */
  HAL_GPIO_WritePin(GPIOC, GPIO_PIN_13|GPIO_PIN_14|GPIO_PIN_15|GPIO_PIN_4 
                          |GPIO_PIN_5|GPIO_PIN_6|GPIO_PIN_7|GPIO_PIN_8 
                          |GPIO_PIN_9|GPIO_PIN_10|GPIO_PIN_11|GPIO_PIN_12, GPIO_PIN_RESET);

  /*Configure GPIO pin Output Level */
  HAL_GPIO_WritePin(GPIOB, GPIO_PIN_10|GPIO_PIN_11|GPIO_PIN_12|GPIO_PIN_13 
                          |GPIO_PIN_14|GPIO_PIN_15|GPIO_PIN_4|GPIO_PIN_5 
                          |GPIO_PIN_6|GPIO_PIN_7|GPIO_PIN_8|GPIO_PIN_9, GPIO_PIN_RESET);

  /*Configure GPIO pin Output Level */
  HAL_GPIO_WritePin(GPIOD, GPIO_PIN_12|GPIO_PIN_13|GPIO_PIN_14|GPIO_PIN_15, GPIO_PIN_RESET);

  /*Configure GPIO pins : PE4 PE5 PE6 PE7 
                           PE8 PE9 PE10 PE11 
                           PE12 PE13 PE14 PE15 */
  GPIO_InitStruct.Pin = GPIO_PIN_4|GPIO_PIN_5|GPIO_PIN_6|GPIO_PIN_7 
                          |GPIO_PIN_8|GPIO_PIN_9|GPIO_PIN_10|GPIO_PIN_11 
                          |GPIO_PIN_12|GPIO_PIN_13|GPIO_PIN_14|GPIO_PIN_15;
  GPIO_InitStruct.Mode = GPIO_MODE_OUTPUT_PP;
  GPIO_InitStruct.Pull = GPIO_NOPULL;
  GPIO_InitStruct.Speed = GPIO_SPEED_FREQ_LOW;
  HAL_GPIO_Init(GPIOE, &GPIO_InitStruct);

  /*Configure GPIO pins : PC13 PC14 PC15 PC4 
                           PC5 PC6 PC7 PC8 
                           PC9 PC10 PC11 PC12 */
  GPIO_InitStruct.Pin = GPIO_PIN_13|GPIO_PIN_14|GPIO_PIN_15|GPIO_PIN_4 
                          |GPIO_PIN_5|GPIO_PIN_6|GPIO_PIN_7|GPIO_PIN_8 
                          |GPIO_PIN_9|GPIO_PIN_10|GPIO_PIN_11|GPIO_PIN_12;
  GPIO_InitStruct.Mode = GPIO_MODE_OUTPUT_PP;
  GPIO_InitStruct.Pull = GPIO_NOPULL;
  GPIO_InitStruct.Speed = GPIO_SPEED_FREQ_LOW;
  HAL_GPIO_Init(GPIOC, &GPIO_InitStruct);

  /*Configure GPIO pins : PB10 PB11 PB12 PB13 
                           PB14 PB15 PB4 PB5 
                           PB6 PB7 PB8 PB9 */
  GPIO_InitStruct.Pin = GPIO_PIN_10|GPIO_PIN_11|GPIO_PIN_12|GPIO_PIN_13 
                          |GPIO_PIN_14|GPIO_PIN_15|GPIO_PIN_4|GPIO_PIN_5 
                          |GPIO_PIN_6|GPIO_PIN_7|GPIO_PIN_8|GPIO_PIN_9;
  GPIO_InitStruct.Mode = GPIO_MODE_OUTPUT_PP;
  GPIO_InitStruct.Pull = GPIO_NOPULL;
  GPIO_InitStruct.Speed = GPIO_SPEED_FREQ_LOW;
  HAL_GPIO_Init(GPIOB, &GPIO_InitStruct);

  /*Configure GPIO pins : PD12 PD13 PD14 PD15 */
  GPIO_InitStruct.Pin = GPIO_PIN_12|GPIO_PIN_13|GPIO_PIN_14|GPIO_PIN_15;
  GPIO_InitStruct.Mode = GPIO_MODE_OUTPUT_PP;
  GPIO_InitStruct.Pull = GPIO_NOPULL;
  GPIO_InitStruct.Speed = GPIO_SPEED_FREQ_LOW;
  HAL_GPIO_Init(GPIOD, &GPIO_InitStruct);

}

/* USER CODE BEGIN 4 */
void Delay(uint32_t Val)
{
	for (; Val != 0; Val--);
}

void changeChannel(uint32_t ch, uint32_t pin)
{
	ADC_ChannelConfTypeDef sConfig = {0};
	ADC_HandleTypeDef* this_hadc;
	sConfig.SamplingTime = ADC_SAMPLETIME_3CYCLES;
	switch (ch)
	{
		case 0:
		{
			sConfig.Rank = 1;
			this_hadc = &hadc1;
			break;
		}
		case 1:
		{
			sConfig.Rank = 2;
			this_hadc = &hadc1;
			break;
		}
		case 2:
		{
			sConfig.Rank = 1;
			this_hadc = &hadc2;
			break;
		}
		case 3:
		{
			sConfig.Rank = 2;
			this_hadc = &hadc2;
			break;
		}
	}
	switch (pin)
	{
		case 0:
		{
			sConfig.Channel = ADC_CHANNEL_0;
			break;
		}
		case 1:
		{
			sConfig.Channel = ADC_CHANNEL_1;
			break;
		}
		case 2:
		{
			sConfig.Channel = ADC_CHANNEL_2;
			break;
		}
		case 3:
		{
			sConfig.Channel = ADC_CHANNEL_3;
			break;
		}
		case 4:
		{
			sConfig.Channel = ADC_CHANNEL_4;
			break;
		}
		case 5:
		{
			sConfig.Channel = ADC_CHANNEL_5;
			break;
		}
		case 6:
		{
			sConfig.Channel = ADC_CHANNEL_6;
			break;
		}
		case 7:
		{
			sConfig.Channel = ADC_CHANNEL_7;
			break;
		}
		case 8:
		{
			sConfig.Channel = ADC_CHANNEL_8;
			break;
		}
		case 9:
		{
			sConfig.Channel = ADC_CHANNEL_9;
			break;
		}
		case 10:
		{
			sConfig.Channel = ADC_CHANNEL_10;
			break;
		}
		case 11:
		{
			sConfig.Channel = ADC_CHANNEL_11;
			break;
		}
	}
	if (HAL_ADC_ConfigChannel(this_hadc, &sConfig) != HAL_OK)
  {
    Error_Handler();
  }
}
void changeProbe(uint32_t pin, uint32_t probe) // probe is 0, 1, 2 or 3
{
	uint32_t* dprobe;
	pin = pin + 4;
	switch (probe)
	{
		case 0:{
			dprobe[0] = 0; dprobe[1] = 0;
			break;
		}
		case 1:{
			dprobe[0] = 0; dprobe[1] = 1;
			break;
		}
		case 2:{
			dprobe[0] = 1; dprobe[1] = 0;
			break;
		}
		case 3:{
			dprobe[0] = 1; dprobe[1] = 1;
			break;
		}
	}
	switch (pin)
	{
		case 4:{
			HAL_GPIO_WritePin(GPIOC, GPIO_PIN_4, dprobe[1]);
			HAL_GPIO_WritePin(GPIOE, GPIO_PIN_4, dprobe[0]);
			break;
		}
		case 5:{
			HAL_GPIO_WritePin(GPIOC, GPIO_PIN_5, dprobe[1]);
			HAL_GPIO_WritePin(GPIOE, GPIO_PIN_5, dprobe[0]);
			break;
		}
		case 6:{
			HAL_GPIO_WritePin(GPIOC, GPIO_PIN_6, dprobe[1]);
			HAL_GPIO_WritePin(GPIOE, GPIO_PIN_6, dprobe[0]);
			break;
		}
		case 7:{
			HAL_GPIO_WritePin(GPIOC, GPIO_PIN_7, dprobe[1]);
			HAL_GPIO_WritePin(GPIOE, GPIO_PIN_7, dprobe[0]);
			break;
		}
		case 8:{
			HAL_GPIO_WritePin(GPIOC, GPIO_PIN_8, dprobe[1]);
			HAL_GPIO_WritePin(GPIOE, GPIO_PIN_8, dprobe[0]);
			break;
		}
		case 9:{
			HAL_GPIO_WritePin(GPIOC, GPIO_PIN_9, dprobe[1]);
			HAL_GPIO_WritePin(GPIOE, GPIO_PIN_9, dprobe[0]);
			break;
		}
		case 10:{
			HAL_GPIO_WritePin(GPIOC, GPIO_PIN_10, dprobe[1]);
			HAL_GPIO_WritePin(GPIOE, GPIO_PIN_10, dprobe[0]);
			break;
		}
		case 11:{
			HAL_GPIO_WritePin(GPIOC, GPIO_PIN_11, dprobe[1]);
			HAL_GPIO_WritePin(GPIOE, GPIO_PIN_11, dprobe[0]);
			break;
		}
		case 12:{
			HAL_GPIO_WritePin(GPIOC, GPIO_PIN_12, dprobe[1]);
			HAL_GPIO_WritePin(GPIOE, GPIO_PIN_12, dprobe[0]);
			break;
		}
		case 13:{
			HAL_GPIO_WritePin(GPIOC, GPIO_PIN_13, dprobe[1]);
			HAL_GPIO_WritePin(GPIOE, GPIO_PIN_13, dprobe[0]);
			break;
		}
		case 14:{
			HAL_GPIO_WritePin(GPIOC, GPIO_PIN_14, dprobe[1]);
			HAL_GPIO_WritePin(GPIOE, GPIO_PIN_14, dprobe[0]);
			break;
		}
		case 15:{
			HAL_GPIO_WritePin(GPIOC, GPIO_PIN_15, dprobe[1]);
			HAL_GPIO_WritePin(GPIOE, GPIO_PIN_15, dprobe[0]);
			break;
		}
	}
}

void changeCoupling(uint32_t pin, uint32_t coup)
{
	pin = pin + 4;
	switch (pin)
	{
		case 4:{
			HAL_GPIO_WritePin(GPIOB, GPIO_PIN_4, coup);
			break;
		}
		case 5:{
			HAL_GPIO_WritePin(GPIOB, GPIO_PIN_5, coup);
			break;
		}
		case 6:{
			HAL_GPIO_WritePin(GPIOB, GPIO_PIN_6, coup);
			break;
		}
		case 7:{
			HAL_GPIO_WritePin(GPIOB, GPIO_PIN_7, coup);
			break;
		}
		case 8:{
			HAL_GPIO_WritePin(GPIOB, GPIO_PIN_8, coup);
			break;
		}
		case 9:{
			HAL_GPIO_WritePin(GPIOB, GPIO_PIN_9, coup);
			break;
		}
		case 10:{
			HAL_GPIO_WritePin(GPIOB, GPIO_PIN_10, coup);
			break;
		}
		case 11:{
			HAL_GPIO_WritePin(GPIOB, GPIO_PIN_11, coup);
			break;
		}
		case 12:{
			HAL_GPIO_WritePin(GPIOB, GPIO_PIN_12, coup);
			break;
		}
		case 13:{
			HAL_GPIO_WritePin(GPIOB, GPIO_PIN_13, coup);
			break;
		}
		case 14:{
			HAL_GPIO_WritePin(GPIOB, GPIO_PIN_14, coup);
			break;
		}
		case 15:{
			HAL_GPIO_WritePin(GPIOB, GPIO_PIN_15, coup);
			break;
		}
	}
}



void applySettings(uint8_t* settings)
{
	if (settings[0] == NULL || settings[1] == NULL || settings[2] == NULL || settings[3] == NULL)
	{
		return;
	}
	uint8_t s, pin, probe, coupling;
	for (uint32_t ch = 0; ch<4; ch++)
	{
		s = settings[ch];
		pin = (uint8_t)(s / 16);
		s = s - pin*16;
		probe = (uint8_t)(s / 4);
		s = s - probe * 4;
		coupling = (uint8_t)(s / 2);
		changeChannel(ch, pin);
		changeProbe(pin, probe);
		changeCoupling(pin, coupling);
		settings[ch] = NULL;
	}
}


/* USER CODE END 4 */

/**
  * @brief  This function is executed in case of error occurrence.
  * @retval None
  */
void Error_Handler(void)
{
  /* USER CODE BEGIN Error_Handler_Debug */
  /* User can add his own implementation to report the HAL error return state */

  /* USER CODE END Error_Handler_Debug */
}

#ifdef  USE_FULL_ASSERT
/**
  * @brief  Reports the name of the source file and the source line number
  *         where the assert_param error has occurred.
  * @param  file: pointer to the source file name
  * @param  line: assert_param error line source number
  * @retval None
  */
void assert_failed(uint8_t *file, uint32_t line)
{ 
  /* USER CODE BEGIN 6 */
  /* User can add his own implementation to report the file name and line number,
     tex: printf("Wrong parameters value: file %s on line %d\r\n", file, line) */
  /* USER CODE END 6 */
}
#endif /* USE_FULL_ASSERT */

/************************ (C) COPYRIGHT STMicroelectronics *****END OF FILE****/
