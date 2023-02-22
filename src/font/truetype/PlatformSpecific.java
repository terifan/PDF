package font.truetype;


enum PlatformSpecific
{
	Version_1_0_semantics,
	Version_1_1_semantics,
	ISO_10646_1993_semantics,
	Unicode_2_0_semantics_BMP_only,
	Unicode_2_0_semantics_non_BMP_allowed,
	Unicode_variation_sequences,
	Full_unicode_coverage,
	//
	// windows, index restarts here ie Symbol really is index 0
	Symbol,
	Unicode_BMP_only,
	Shift_JIS,
	PRC,
	BigFive,
	Johab,
	unused6,
	unused7,
	unused8,
	unused9,
	Unicode
}
