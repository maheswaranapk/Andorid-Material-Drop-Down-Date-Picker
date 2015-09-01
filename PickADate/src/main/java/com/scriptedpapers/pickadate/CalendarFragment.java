package com.scriptedpapers.pickadate;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.scriptedpapers.pickadate.viewpager.InfinitePagerAdapter;
import com.scriptedpapers.pickadate.viewpager.InfiniteViewPager;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import hirondelle.date4j.DateTime;


@SuppressLint("DefaultLocale")
public class CalendarFragment extends Fragment {
    public String TAG = "CalendarFragment";

    boolean toggleCalendar = true;

    public static final String PICK_A_DATE = "Pick A Date";

    /**
     * Weekday conventions
     */
    public static int
            SUNDAY = 1,
            MONDAY = 2,
            TUESDAY = 3,
            WEDNESDAY = 4,
            THURSDAY = 5,
            FRIDAY = 6,
            SATURDAY = 7;

    private static final int MONTH_YEAR_FLAG = DateUtils.FORMAT_SHOW_DATE
            | DateUtils.FORMAT_NO_MONTH_DAY | DateUtils.FORMAT_SHOW_YEAR;


    private Time firstMonthTime = new Time();

    private final StringBuilder monthYearStringBuilder = new StringBuilder(50);
    private Formatter monthYearFormatter = new Formatter(
            monthYearStringBuilder, Locale.getDefault());

    public final static int NUMBER_OF_PAGES = 4;

    private ImageView leftArrowButton;
    private ImageView rightArrowButton;
    private TextView monthTitleTextView;
    private TopLineView topLineView;
    private TextView animationTextView;

    int height;

    int textViewDoublePadding;


    int overAllHeight;

    RelativeLayout calendarTitleView;
            LinearLayout calendarView;

    ObjectAnimator hideCalendarAnimator;
    ObjectAnimator showCalendarAnimator;

    ObjectAnimator monthTextMarginAnimator;

    ObjectAnimator leftAlphaAnimator;
    ObjectAnimator rightAlphaAnimator;

    ObjectAnimator leftArrowMovement;
    ObjectAnimator rightArrowMovement;

    private GridView weekdayGridView;
    private InfiniteViewPager dateViewPager;
    private DatePageChangeListener pageChangeListener;
    private ArrayList<DateGridFragment> fragments;

    boolean updateSingleColumnWidth;

    private int themeResource = R.style.CaldroidDefault;

    public final static String
            MONTH = "month",
            YEAR = "year",
            DISABLE_DATES = "disableDates",
            SELECTED_DATES = "selectedDates",
            MIN_DATE = "minDate",
            MAX_DATE = "maxDate",
            START_DAY_OF_WEEK = "startDayOfWeek",
            SQUARE_TEXT_VIEW_CELL = "squareTextViewCell",
            THEME_RESOURCE = "themeResource";

    public final static String
            _MIN_DATE_TIME = "_minDateTime",
            _MAX_DATE_TIME = "_maxDateTime",
            _BACKGROUND_FOR_DATETIME_MAP = "_backgroundForDateTimeMap",
            _TEXT_COLOR_FOR_DATETIME_MAP = "_textColorForDateTimeMap";

    protected int month = -1;
    protected int year = -1;
    protected ArrayList<DateTime> disableDates = new ArrayList<DateTime>();
    protected DateTime selectedDate;
    protected DateTime minDateTime;
    protected DateTime maxDateTime;
    protected ArrayList<DateTime> dateInMonthsList;

    protected HashMap<String, Object> caldroidData = new HashMap<String, Object>();

    protected HashMap<String, Object> extraData = new HashMap<String, Object>();

    protected HashMap<DateTime, Integer> backgroundForDateTimeMap = new HashMap<DateTime, Integer>();

    protected HashMap<DateTime, Integer> textColorForDateTimeMap = new HashMap<DateTime, Integer>();

    protected int startDayOfWeek = SUNDAY;

    protected ArrayList<CaldroidGridAdapter> datePagerAdapters = new ArrayList<CaldroidGridAdapter>();

    protected boolean squareTextViewCell;

    private OnItemClickListener dateItemClickListener;

    private OnItemLongClickListener dateItemLongClickListener;

    private CaldroidListener caldroidListener;

    public CaldroidListener getCaldroidListener() {
        return caldroidListener;
    }

    public CaldroidGridAdapter getNewDatesGridAdapter(int month, int year) {
        return new CaldroidGridAdapter(getActivity(), month, year,
                getCaldroidData(), extraData);
    }

    public WeekdayArrayAdapter getNewWeekdayAdapter() {
        return new WeekdayArrayAdapter(
                getActivity(), android.R.layout.simple_list_item_1,
                getDaysOfWeek());
    }

    public GridView getWeekdayGridView() {
        return weekdayGridView;
    }

    public ArrayList<DateGridFragment> getFragments() {
        return fragments;
    }

    public InfiniteViewPager getDateViewPager() {
        return dateViewPager;
    }

    public HashMap<DateTime, Integer> getBackgroundForDateTimeMap() {
        return backgroundForDateTimeMap;
    }

    public HashMap<DateTime, Integer> getTextColorForDateTimeMap() {
        return textColorForDateTimeMap;
    }

    public ImageView getLeftArrowButton() {
        return leftArrowButton;
    }

    public ImageView getRightArrowButton() {
        return rightArrowButton;
    }

    public TextView getMonthTitleTextView() {
        return monthTitleTextView;
    }

    public void setMonthTitleTextView(TextView monthTitleTextView) {
        this.monthTitleTextView = monthTitleTextView;
    }

    public ArrayList<CaldroidGridAdapter> getDatePagerAdapters() {
        return datePagerAdapters;
    }

    public HashMap<String, Object> getCaldroidData() {
        caldroidData.clear();
        caldroidData.put(DISABLE_DATES, disableDates);
        caldroidData.put(SELECTED_DATES, selectedDate);
        caldroidData.put(_MIN_DATE_TIME, minDateTime);
        caldroidData.put(_MAX_DATE_TIME, maxDateTime);
        caldroidData.put(START_DAY_OF_WEEK, Integer.valueOf(startDayOfWeek));
        caldroidData.put(SQUARE_TEXT_VIEW_CELL, squareTextViewCell);
        caldroidData.put(THEME_RESOURCE, themeResource);

        caldroidData
                .put(_BACKGROUND_FOR_DATETIME_MAP, backgroundForDateTimeMap);
        caldroidData.put(_TEXT_COLOR_FOR_DATETIME_MAP, textColorForDateTimeMap);

        return caldroidData;
    }

    public HashMap<String, Object> getExtraData() {
        return extraData;
    }

    public void setExtraData(HashMap<String, Object> extraData) {
        this.extraData = extraData;
    }

    public void setBackgroundResourceForDates(
            HashMap<Date, Integer> backgroundForDateMap) {
        if (backgroundForDateMap == null || backgroundForDateMap.size() == 0) {
            return;
        }

        backgroundForDateTimeMap.clear();

        for (Date date : backgroundForDateMap.keySet()) {
            Integer resource = backgroundForDateMap.get(date);
            DateTime dateTime = CalendarHelper.convertDateToDateTime(date);
            backgroundForDateTimeMap.put(dateTime, resource);
        }
    }

    public void clearBackgroundResourceForDates(List<Date> dates) {
        if (dates == null || dates.size() == 0) {
            return;
        }

        for (Date date : dates) {
            clearBackgroundResourceForDate(date);
        }
    }

    public void setBackgroundResourceForDateTimes(
            HashMap<DateTime, Integer> backgroundForDateTimeMap) {
        this.backgroundForDateTimeMap.putAll(backgroundForDateTimeMap);
    }

    public void clearBackgroundResourceForDateTimes(List<DateTime> dateTimes) {
        if (dateTimes == null || dateTimes.size() == 0) return;

        for (DateTime dateTime : dateTimes) {
            backgroundForDateTimeMap.remove(dateTime);
        }
    }

    public void setBackgroundResourceForDate(int backgroundRes, Date date) {
        DateTime dateTime = CalendarHelper.convertDateToDateTime(date);
        backgroundForDateTimeMap.put(dateTime, backgroundRes);
    }

    public void clearBackgroundResourceForDate(Date date) {
        DateTime dateTime = CalendarHelper.convertDateToDateTime(date);
        backgroundForDateTimeMap.remove(dateTime);
    }

    public void setBackgroundResourceForDateTime(int backgroundRes,
                                                 DateTime dateTime) {
        backgroundForDateTimeMap.put(dateTime, backgroundRes);
    }

    public void clearBackgroundResourceForDateTime(DateTime dateTime) {
        backgroundForDateTimeMap.remove(dateTime);
    }

    public void setTextColorForDates(HashMap<Date, Integer> textColorForDateMap) {
        if (textColorForDateMap == null || textColorForDateMap.size() == 0) {
            return;
        }

        textColorForDateTimeMap.clear();

        for (Date date : textColorForDateMap.keySet()) {
            Integer resource = textColorForDateMap.get(date);
            DateTime dateTime = CalendarHelper.convertDateToDateTime(date);
            textColorForDateTimeMap.put(dateTime, resource);
        }
    }

    public void clearTextColorForDates(List<Date> dates) {
        if (dates == null || dates.size() == 0) return;

        for (Date date : dates) {
            clearTextColorForDate(date);
        }
    }

    public void setTextColorForDateTimes(
            HashMap<DateTime, Integer> textColorForDateTimeMap) {
        this.textColorForDateTimeMap.putAll(textColorForDateTimeMap);
    }

    public void setTextColorForDate(int textColorRes, Date date) {
        DateTime dateTime = CalendarHelper.convertDateToDateTime(date);
        textColorForDateTimeMap.put(dateTime, textColorRes);
    }

    public void clearTextColorForDate(Date date) {
        DateTime dateTime = CalendarHelper.convertDateToDateTime(date);
        textColorForDateTimeMap.remove(dateTime);
    }

    public void setTextColorForDateTime(int textColorRes, DateTime dateTime) {
        textColorForDateTimeMap.put(dateTime, textColorRes);
    }

    public Bundle getSavedStates() {
        Bundle bundle = new Bundle();
        bundle.putInt(MONTH, month);
        bundle.putInt(YEAR, year);

        if (selectedDate != null) {
            bundle.putString(SELECTED_DATES,
                    CalendarHelper.convertToStringList(selectedDate));
        }

        if (disableDates != null && disableDates.size() > 0) {
            bundle.putStringArrayList(DISABLE_DATES,
                    CalendarHelper.convertToStringList(disableDates));
        }

        if (minDateTime != null) {
            bundle.putString(MIN_DATE, minDateTime.format("YYYY-MM-DD"));
        }

        if (maxDateTime != null) {
            bundle.putString(MAX_DATE, maxDateTime.format("YYYY-MM-DD"));
        }
        bundle.putInt(START_DAY_OF_WEEK, startDayOfWeek);
        bundle.putInt(THEME_RESOURCE, themeResource);

        return bundle;
    }

    public int getCurrentVirtualPosition() {
        int currentPage = dateViewPager.getCurrentItem();
        return pageChangeListener.getCurrent(currentPage);
    }

    public void moveToDate(Date date) {
        moveToDateTime(CalendarHelper.convertDateToDateTime(date));
    }

    public void moveToDateTime(DateTime dateTime) {

        DateTime firstOfMonth = new DateTime(year, month, 1, 0, 0, 0, 0);
        DateTime lastOfMonth = firstOfMonth.getEndOfMonth();

        if (dateTime.lt(firstOfMonth)) {

            DateTime firstDayNextMonth = dateTime.plus(0, 1, 0, 0, 0, 0, 0,
                    DateTime.DayOverflow.LastDay);

            pageChangeListener.setCurrentDateTime(firstDayNextMonth);
            int currentItem = dateViewPager.getCurrentItem();
            pageChangeListener.refreshAdapters(currentItem);

            dateViewPager.setCurrentItem(currentItem - 1);
        }

        else if (dateTime.gt(lastOfMonth)) {

            DateTime firstDayLastMonth = dateTime.minus(0, 1, 0, 0, 0, 0, 0,
                    DateTime.DayOverflow.LastDay);

            pageChangeListener.setCurrentDateTime(firstDayLastMonth);
            int currentItem = dateViewPager.getCurrentItem();
            pageChangeListener.refreshAdapters(currentItem);

            dateViewPager.setCurrentItem(currentItem + 1);
        }

    }

    public void setCalendarDate(Date date) {
        setCalendarDateTime(CalendarHelper.convertDateToDateTime(date));
    }

    public void setCalendarDateTime(DateTime dateTime) {
        month = dateTime.getMonth();
        year = dateTime.getYear();

        // Notify listener
        if (caldroidListener != null) {
            caldroidListener.onChangeMonth(month, year);
        }

        refreshView();
    }

    public void prevMonth() {
        dateViewPager.setCurrentItem(pageChangeListener.getCurrentPage() - 1);
    }

    public void nextMonth() {
        dateViewPager.setCurrentItem(pageChangeListener.getCurrentPage() + 1);
    }

    public void clearDisableDates() {
        disableDates.clear();
    }

    public void setDisableDates(ArrayList<Date> disableDateList) {
        if (disableDateList == null || disableDateList.size() == 0) {
            return;
        }

        disableDates.clear();

        for (Date date : disableDateList) {
            DateTime dateTime = CalendarHelper.convertDateToDateTime(date);
            disableDates.add(dateTime);
        }
    }

    public void setDisableDatesFromString(ArrayList<String> disableDateStrings) {
        setDisableDatesFromString(disableDateStrings, null);
    }

    public void setDisableDatesFromString(ArrayList<String> disableDateStrings,
                                          String dateFormat) {
        if (disableDateStrings == null) {
            return;
        }

        disableDates.clear();

        for (String dateString : disableDateStrings) {
            DateTime dateTime = CalendarHelper.getDateTimeFromString(
                    dateString, dateFormat);
            disableDates.add(dateTime);
        }
    }

    public void clearSelectedDates() {
        selectedDate = null;
    }

    public void setSelectedDates(Date fromDate, Date toDate) {
        // Ensure fromDate is before toDate
        if (fromDate == null || toDate == null || fromDate.after(toDate)) {
            return;
        }

        clearSelectedDates();

        DateTime fromDateTime = CalendarHelper.convertDateToDateTime(fromDate);
        DateTime toDateTime = CalendarHelper.convertDateToDateTime(toDate);

        DateTime dateTime = fromDateTime;
        while (dateTime.lt(toDateTime)) {
            selectedDate = dateTime;
        }

    }

    public void setSelectedDateStrings(String fromDateString,
                                       String toDateString, String dateFormat) throws ParseException {

        Date fromDate = CalendarHelper.getDateFromString(fromDateString,
                dateFormat);
        Date toDate = CalendarHelper
                .getDateFromString(toDateString, dateFormat);
        setSelectedDates(fromDate, toDate);
    }

    public void setMinDate(Date minDate) {
        if (minDate == null) {
            minDateTime = null;
        } else {
            minDateTime = CalendarHelper.convertDateToDateTime(minDate);
        }
    }

    public void setMinDateFromString(String minDateString, String dateFormat) {
        if (minDateString == null) {
            setMinDate(null);
        } else {
            minDateTime = CalendarHelper.getDateTimeFromString(minDateString,
                    dateFormat);
        }
    }

    /**
     * Set max date. This method does not refresh view
     *
     * @param maxDate
     */
    public void setMaxDate(Date maxDate) {
        if (maxDate == null) {
            maxDateTime = null;
        } else {
            maxDateTime = CalendarHelper.convertDateToDateTime(maxDate);
        }
    }

    public void setMaxDateFromString(String maxDateString, String dateFormat) {
        if (maxDateString == null) {
            setMaxDate(null);
        } else {
            maxDateTime = CalendarHelper.getDateTimeFromString(maxDateString,
                    dateFormat);
        }
    }

    public void setCaldroidListener(CaldroidListener caldroidListener) {
        this.caldroidListener = caldroidListener;
    }

    public OnItemClickListener getDateItemClickListener() {
        if (dateItemClickListener == null) {
            dateItemClickListener = new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    DateTime dateTime = dateInMonthsList.get(position);

                    int xPosition;
                    int yPosition;

                    xPosition = position % 7;
                    yPosition = (position/7) + 1;

                    selectedDate = dateTime;

                    refreshView();

                    animationTextView.getLayoutParams().height = (int) topLineView.getSingleColumnWidth();
                    animationTextView.getLayoutParams().width = (int) topLineView.getSingleColumnWidth();

                    float margin = getActivity().getResources().getDimension(R.dimen.default_margin);

                    animationTextView.setX(margin + (xPosition * topLineView.getSingleColumnWidth()));
                    animationTextView.setY(calendarTitleView.getHeight() + (yPosition * topLineView.getSingleColumnWidth()));

                    animationTextView.setVisibility(View.VISIBLE);
                    animationTextView.bringToFront();

                    // TODO:

                    animationTextView.setText(""+dateTime.getDay());

                    float toX = margin;
                    float toY = (calendarTitleView.getHeight()/2) - (topLineView.getSingleColumnWidth()/2);

                    ObjectAnimator verticalAnimator7 = ObjectAnimator.ofFloat(animationTextView, "x", animationTextView.getX(), toX);
                    verticalAnimator7.setStartDelay(0);
                    verticalAnimator7.setDuration(1000);
                    verticalAnimator7.start();

                    ObjectAnimator horizontalAnimator = ObjectAnimator.ofFloat(animationTextView, "y", animationTextView.getY(), toY);
                    horizontalAnimator.setStartDelay(0);
                    horizontalAnimator.setDuration(1000);
                    horizontalAnimator.start();


                    ValueAnimator animator = ValueAnimator.ofInt(4, 0);
                    animator.setDuration(1000);

                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int value = ((int) (animation.getAnimatedValue()));

                            GradientDrawable gd = new GradientDrawable();
                            gd.setColor(0x00000000);
                            gd.setStroke(value, 0xFF000000);

                            animationTextView.setBackgroundDrawable(gd);
                        }
                    });
                    animator.start();

//                    Toast.makeText(getActivity(), "calendarView.getX(): "+animationTextView.getX()+" calendarView.gety(): "+animationTextView.getY(), Toast.LENGTH_SHORT).show();

                    if (caldroidListener != null) {
                        Date date = CalendarHelper
                                .convertDateTimeToDate(dateTime);
                        caldroidListener.onSelectDate(date, view);
                    }

                    hideCalendar();
                }
            };
        }

        return dateItemClickListener;
    }

    public OnItemLongClickListener getDateItemLongClickListener() {
        if (dateItemLongClickListener == null) {
            dateItemLongClickListener = new OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent,
                                               View view, int position, long id) {

                    DateTime dateTime = dateInMonthsList.get(position);

                    if (caldroidListener != null) {

                        Date date = CalendarHelper
                                .convertDateTimeToDate(dateTime);
                        caldroidListener.onLongClickDate(date, view);
                    }

                    return true;
                }
            };
        }

        return dateItemLongClickListener;
    }

    protected void refreshMonthTitleTextView() {
        // Refresh title view
        firstMonthTime.year = year;
        firstMonthTime.month = month - 1;
        firstMonthTime.monthDay = 1;
        long millis = firstMonthTime.toMillis(true);

        monthYearStringBuilder.setLength(0);
        String monthTitle = DateUtils.formatDateRange(getActivity(),
                monthYearFormatter, millis, millis, MONTH_YEAR_FLAG).toString();

        monthTitleTextView.setText(monthTitle.toUpperCase(Locale.getDefault()));
    }

    public void refreshView() {

        if (month == -1 || year == -1) {
            return;
        }

        refreshMonthTitleTextView();

        // Refresh the date grid views
        for (CaldroidGridAdapter adapter : datePagerAdapters) {
            // Reset caldroid data
            adapter.setCaldroidData(getCaldroidData());

            // Reset extra data
            adapter.setExtraData(extraData);

            // Update today variable
            adapter.updateToday();

            // Refresh view
            adapter.notifyDataSetChanged();
        }
    }

    protected void retrieveInitialArgs() {
        // Get arguments
        Bundle args = getArguments();
        if (args != null) {
            // Get month, year
            month = args.getInt(MONTH, -1);
            year = args.getInt(YEAR, -1);


            // Get start day of Week. Default calendar first column is SUNDAY
            startDayOfWeek = args.getInt(START_DAY_OF_WEEK, 1);
            if (startDayOfWeek > 7) {
                startDayOfWeek = startDayOfWeek % 7;
            }

            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                squareTextViewCell = args.getBoolean(SQUARE_TEXT_VIEW_CELL, true);
            } else {
                squareTextViewCell = args.getBoolean(SQUARE_TEXT_VIEW_CELL, false);
            }

            // Get disable dates
            ArrayList<String> disableDateStrings = args
                    .getStringArrayList(DISABLE_DATES);
            if (disableDateStrings != null && disableDateStrings.size() > 0) {
                disableDates.clear();
                for (String dateString : disableDateStrings) {
                    DateTime dt = CalendarHelper.getDateTimeFromString(
                            dateString, "yyyy-MM-dd");
                    disableDates.add(dt);
                }
            }

            // Get selected dates
            String selectedDateString = args
                    .getString(SELECTED_DATES);
            if (selectedDateString != null ) {
                clearSelectedDates();
                    selectedDate = CalendarHelper.getDateTimeFromString(
                            selectedDateString, "yyyy-MM-dd");
            }

            // Get min date and max date
            String minDateTimeString = args.getString(MIN_DATE);
            if (minDateTimeString != null) {
                minDateTime = CalendarHelper.getDateTimeFromString(
                        minDateTimeString, null);
            }

            String maxDateTimeString = args.getString(MAX_DATE);
            if (maxDateTimeString != null) {
                maxDateTime = CalendarHelper.getDateTimeFromString(
                        maxDateTimeString, null);
            }

            // Get theme
            themeResource = args.getInt(THEME_RESOURCE, R.style.CaldroidDefault);
        }
        if (month == -1 || year == -1) {
            DateTime dateTime = DateTime.today(TimeZone.getDefault());
            month = dateTime.getMonth();
            year = dateTime.getYear();
        }
    }

    public static CalendarFragment newInstance(String dialogTitle, int month,
                                               int year) {
        CalendarFragment f = new CalendarFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt(MONTH, month);
        args.putInt(YEAR, year);

        f.setArguments(args);

        return f;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void setThemeResource(int id) {
        themeResource = id;
    }

    public int getThemeResource() {
        return themeResource;
    }

    public static LayoutInflater getLayoutInflater(Context context, LayoutInflater origInflater, int themeResource) {
        Context wrapped = new ContextThemeWrapper(context, themeResource);
        return origInflater.cloneInContext(wrapped);
    }

    Animation slideIn;
    Animation slideOut;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        retrieveInitialArgs();

        toggleCalendar = true;


        LayoutInflater localInflater = getLayoutInflater(getActivity(), inflater, themeResource);

        View view = localInflater.inflate(R.layout.calendar_view, container, false);

        topLineView = (TopLineView) view.findViewById(R.id.topLineView);
        monthTitleTextView = (TextView) view.findViewById(R.id.calendar_month_year_textview);

        animationTextView = (TextView) view.findViewById(R.id.animationTextView);

        calendarTitleView = (RelativeLayout) view.findViewById(R.id.calendarTitleView);
        calendarView = (LinearLayout) view.findViewById(R.id.calendarView);


        // For the left arrow button
        leftArrowButton = (ImageView) view.findViewById(R.id.calendar_left_arrow);
        rightArrowButton = (ImageView) view.findViewById( R.id.calendar_right_arrow );

        // Navigate to previous month when user click
        leftArrowButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (leftArrowButton.getAlpha() != 0)
                    prevMonth();
                else
                    calendarTitleView.performClick();
            }
        });

        // Navigate to next month when user click
        rightArrowButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (rightArrowButton.getAlpha() != 0)
                    nextMonth();
                else
                    calendarTitleView.performClick();
            }
        });

        leftArrowButton.setVisibility(View.GONE);
        rightArrowButton.setVisibility(View.GONE);

        calendarView.setVisibility(View.GONE);

        animationTextView.setVisibility(View.INVISIBLE);

        calendarTitleView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                calendarView.setVisibility(View.VISIBLE);
                leftArrowButton.setVisibility(View.VISIBLE);
                rightArrowButton.setVisibility(View.VISIBLE);

                leftArrowButton.setAlpha(0.0F);
                rightArrowButton.setAlpha(0.0F);


                height = calendarTitleView.getWidth() * 30 / 100;

                textViewDoublePadding = (int) getActivity().getResources().getDimension(R.dimen.default_margin);

                overAllHeight = calendarTitleView.getWidth() - (2 * textViewDoublePadding) - monthTitleTextView.getWidth();

                overAllHeight = overAllHeight / 2;


                topLineView.setVariableProperty(calendarTitleView.getWidth(), weekdayGridView);

                if (toggleCalendar) {



                    if(selectedDate != null) {

                        month = selectedDate.getMonth();
                        year = selectedDate.getYear();

                        monthTextMarginAnimator = ObjectAnimator.ofFloat(CalendarFragment.this, "monthTextMargin", topLineView.getSingleColumnWidth(), overAllHeight);
                        monthTextMarginAnimator.setStartDelay(0);
                        monthTextMarginAnimator.setDuration(500);
                        monthTextMarginAnimator.start();

                        moveToDateTime(selectedDate);

                    } else {



                        monthTextMarginAnimator = ObjectAnimator.ofFloat(CalendarFragment.this, "monthTextMargin", 0, overAllHeight);
                        monthTextMarginAnimator.setStartDelay(0);
                        monthTextMarginAnimator.setDuration(500);
                        monthTextMarginAnimator.start();

                        moveToDate(new Date());
                    }


                    showCalendarAnimator = ObjectAnimator.ofFloat(calendarView, "Y", -calendarView.getHeight(), 0);
                    showCalendarAnimator.setStartDelay(0);
                    showCalendarAnimator.setDuration(1000);
                    showCalendarAnimator.start();

                    animationTextView.setVisibility(View.INVISIBLE);

                    topLineView.moveToEdge();


                    leftAlphaAnimator = ObjectAnimator.ofFloat(leftArrowButton, "alpha", 0, 1);
                    leftAlphaAnimator.setStartDelay(0);
                    leftAlphaAnimator.setDuration(500);
                    leftAlphaAnimator.setStartDelay(500);
                    leftAlphaAnimator.start();

                    rightAlphaAnimator = ObjectAnimator.ofFloat(rightArrowButton, "alpha", 0, 1);
                    rightAlphaAnimator.setStartDelay(0);
                    rightAlphaAnimator.setDuration(500);
                    rightAlphaAnimator.setStartDelay(500);
                    rightAlphaAnimator.start();


                    leftArrowMovement = ObjectAnimator.ofFloat(CalendarFragment.this, "leftArrowMargin", height, 0);
                    leftArrowMovement.setStartDelay(0);
                    leftArrowMovement.setDuration(500);
                    leftArrowMovement.setStartDelay(500);
                    leftArrowMovement.start();

                    rightArrowMovement = ObjectAnimator.ofFloat(CalendarFragment.this, "rightArrowMargin", height, 0);
                    rightArrowMovement.setStartDelay(0);
                    rightArrowMovement.setDuration(500);
                    rightArrowMovement.setStartDelay(500);
                    rightArrowMovement.start();

                    toggleCalendar = false;

                    refreshMonthTitleTextView();
                } else {


                    hideCalendar();

                }
            }
        });


        // For the weekday gridview ("SUN, MON, TUE, WED, THU, FRI, SAT")
        weekdayGridView = (GridView) view.findViewById(R.id.weekday_gridview);
        WeekdayArrayAdapter weekdaysAdapter = getNewWeekdayAdapter();
        weekdayGridView.setAdapter(weekdaysAdapter);

        // Setup all the pages of date grid views. These pages are recycled
        setupDateGridPages(view);

        // Refresh view
        refreshView();

        updateSingleColumnWidth = false;

        calendarTitleView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {

                        if(!updateSingleColumnWidth) {
                        topLineView.setVariableProperty(calendarTitleView.getWidth(), weekdayGridView);

                        if (selectedDate == null) {
                            monthTitleTextView.setText(PICK_A_DATE);
                        } else {

                            float margin = getActivity().getResources().getDimension(R.dimen.default_margin);

                            float toX = margin;
                            float toY = (calendarTitleView.getHeight() / 2) - (topLineView.getSingleColumnWidth() / 2);

                            animationTextView.setVisibility(View.VISIBLE);

                            animationTextView.setX(toX);
                            animationTextView.setY(toY);

                            animationTextView.getLayoutParams().width = (int) topLineView.getSingleColumnWidth();
                            animationTextView.getLayoutParams().height = (int) topLineView.getSingleColumnWidth();

                            animationTextView.setText("" + selectedDate.getDay());

                            setMonthTextMargin(topLineView.getSingleColumnWidth());

                            monthTitleTextView.setText(selectedDate.format("MMMM YYYY", Locale.getDefault()).toUpperCase(Locale.getDefault()));
                        }

                            updateSingleColumnWidth = true;

                        }

                    }
                });


        return view;
    }

    void hideCalendar() {

        hideCalendarAnimator = ObjectAnimator.ofFloat(calendarView, "Y", 0, -calendarView.getHeight());
        hideCalendarAnimator.setStartDelay(500);
        hideCalendarAnimator.setDuration(1000);
        hideCalendarAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                if(selectedDate == null) {
                    monthTitleTextView.setText(PICK_A_DATE);
                } else {
                    monthTitleTextView.setText(selectedDate.format("MMMM YYYY", Locale.getDefault()).toUpperCase(Locale.getDefault()));
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        hideCalendarAnimator.start();


        if(selectedDate != null) {


            monthTextMarginAnimator = ObjectAnimator.ofFloat(CalendarFragment.this, "monthTextMargin", overAllHeight , topLineView.getSingleColumnWidth());
            monthTextMarginAnimator.setStartDelay(500);
            monthTextMarginAnimator.setDuration(500);
            monthTextMarginAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {

                    animationTextView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                }
            });
            monthTextMarginAnimator.start();
        } else {
            monthTextMarginAnimator = ObjectAnimator.ofFloat(CalendarFragment.this, "monthTextMargin", overAllHeight, 0);
            monthTextMarginAnimator.setStartDelay(500);
            monthTextMarginAnimator.setDuration(500);
            monthTextMarginAnimator.start();
        }

        topLineView.moveToBack();

        leftAlphaAnimator = ObjectAnimator.ofFloat(leftArrowButton, "alpha", 1, 0);
        leftAlphaAnimator.setStartDelay(0);
        leftAlphaAnimator.setDuration(500);
        leftAlphaAnimator.start();

        rightAlphaAnimator = ObjectAnimator.ofFloat(rightArrowButton, "alpha", 1, 0);
        rightAlphaAnimator.setStartDelay(0);
        rightAlphaAnimator.setDuration(500);
        rightAlphaAnimator.start();

        leftArrowMovement = ObjectAnimator.ofFloat(CalendarFragment.this, "leftArrowMargin", 0, height);
        leftArrowMovement.setStartDelay(0);
        leftArrowMovement.setDuration(500);
        leftArrowMovement.start();

        rightArrowMovement = ObjectAnimator.ofFloat(CalendarFragment.this, "rightArrowMargin", 0, height);
        rightArrowMovement.setStartDelay(0);
        rightArrowMovement.setDuration(500);
        rightArrowMovement.start();


        toggleCalendar = true;
    }

    public void setMonthTextMargin(float margin) {

        RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) monthTitleTextView.getLayoutParams();
        rl.setMargins((int) margin, 0, 0, 0);

        monthTitleTextView.setLayoutParams(rl);
    }

    public void setLeftArrowMargin(float margin) {

        RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) leftArrowButton.getLayoutParams();
        rl.setMargins((int) margin, 0, 0, 0);

        leftArrowButton.setLayoutParams(rl);
    }

    public void setRightArrowMargin(float margin) {

        RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) rightArrowButton.getLayoutParams();
        rl.setMargins(0, 0, (int) margin, 0);

        rightArrowButton.setLayoutParams(rl);

    }

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		if (caldroidListener != null) {
			caldroidListener.onCaldroidViewCreated();
		}


	}

    protected int getGridViewRes() {
        return R.layout.date_grid_fragment;
    }

    private void setupDateGridPages(View view) {
        // Get current date time
        DateTime currentDateTime = new DateTime(year, month, 1, 0, 0, 0, 0);

        // Set to pageChangeListener
        pageChangeListener = new DatePageChangeListener();
        pageChangeListener.setCurrentDateTime(currentDateTime);

        // Setup adapters for the grid views
        // Current month
        CaldroidGridAdapter adapter0 = getNewDatesGridAdapter(
                currentDateTime.getMonth(), currentDateTime.getYear());

        // Setup dateInMonthsList
        dateInMonthsList = adapter0.getDatetimeList();

        // Next month
        DateTime nextDateTime = currentDateTime.plus(0, 1, 0, 0, 0, 0, 0,
                DateTime.DayOverflow.LastDay);
        CaldroidGridAdapter adapter1 = getNewDatesGridAdapter(
                nextDateTime.getMonth(), nextDateTime.getYear());

        // Next 2 month
        DateTime next2DateTime = nextDateTime.plus(0, 1, 0, 0, 0, 0, 0,
                DateTime.DayOverflow.LastDay);
        CaldroidGridAdapter adapter2 = getNewDatesGridAdapter(
                next2DateTime.getMonth(), next2DateTime.getYear());

        // Previous month
        DateTime prevDateTime = currentDateTime.minus(0, 1, 0, 0, 0, 0, 0,
                DateTime.DayOverflow.LastDay);
        CaldroidGridAdapter adapter3 = getNewDatesGridAdapter(
                prevDateTime.getMonth(), prevDateTime.getYear());

        // Add to the array of adapters
        datePagerAdapters.add(adapter0);
        datePagerAdapters.add(adapter1);
        datePagerAdapters.add(adapter2);
        datePagerAdapters.add(adapter3);

        // Set adapters to the pageChangeListener so it can refresh the adapter
        // when page change
        pageChangeListener.setCaldroidGridAdapters(datePagerAdapters);

        // Setup InfiniteViewPager and InfinitePagerAdapter. The
        // InfinitePagerAdapter is responsible
        // for reuse the fragments
        dateViewPager = (InfiniteViewPager) view
                .findViewById(R.id.months_infinite_pager);

        // Set the numberOfDaysInMonth to dateViewPager so it can calculate the
        // height correctly
        dateViewPager.setDatesInMonth(dateInMonthsList);

        // MonthPagerAdapter actually provides 4 real fragments. The
        // InfinitePagerAdapter only recycles fragment provided by this
        // MonthPagerAdapter
        final MonthPagerAdapter pagerAdapter = new MonthPagerAdapter(
                getChildFragmentManager());

        // Provide initial data to the fragments, before they are attached to
        // view.
        fragments = pagerAdapter.getFragments();

        for (int i = 0; i < NUMBER_OF_PAGES; i++) {
            DateGridFragment dateGridFragment = fragments.get(i);
            CaldroidGridAdapter adapter = datePagerAdapters.get(i);
            dateGridFragment.setGridViewRes(getGridViewRes());
            dateGridFragment.setGridAdapter(adapter);
            dateGridFragment.setOnItemClickListener(getDateItemClickListener());
            dateGridFragment
                    .setOnItemLongClickListener(getDateItemLongClickListener());
        }

        // Setup InfinitePagerAdapter to wrap around MonthPagerAdapter
        InfinitePagerAdapter infinitePagerAdapter = new InfinitePagerAdapter(
                pagerAdapter);

        // Use the infinitePagerAdapter to provide data for dateViewPager
        dateViewPager.setAdapter(infinitePagerAdapter);

        // Setup pageChangeListener
        dateViewPager.setOnPageChangeListener(pageChangeListener);
    }

    protected ArrayList<String> getDaysOfWeek() {
        ArrayList<String> list = new ArrayList<String>();

        SimpleDateFormat fmt = new SimpleDateFormat("EEE", Locale.getDefault());

        // 17 Feb 2013 is Sunday
        DateTime sunday = new DateTime(2013, 2, 17, 0, 0, 0, 0);
        DateTime nextDay = sunday.plusDays(startDayOfWeek - SUNDAY);

        for (int i = 0; i < 7; i++) {
            Date date = CalendarHelper.convertDateTimeToDate(nextDay);
            list.add(fmt.format(date).toUpperCase());
            nextDay = nextDay.plusDays(1);
        }

        return list;
    }

    public class DatePageChangeListener implements OnPageChangeListener {
        private int currentPage = InfiniteViewPager.OFFSET;
        private DateTime currentDateTime;
        private ArrayList<CaldroidGridAdapter> caldroidGridAdapters;

        /**
         * Return currentPage of the dateViewPager
         *
         * @return
         */
        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        /**
         * Return currentDateTime of the selected page
         *
         * @return
         */
        public DateTime getCurrentDateTime() {
            return currentDateTime;
        }

        public void setCurrentDateTime(DateTime dateTime) {
            this.currentDateTime = dateTime;
            setCalendarDateTime(currentDateTime);
        }

        /**
         * Return 4 adapters
         *
         * @return
         */
        public ArrayList<CaldroidGridAdapter> getCaldroidGridAdapters() {
            return caldroidGridAdapters;
        }

        public void setCaldroidGridAdapters(
                ArrayList<CaldroidGridAdapter> caldroidGridAdapters) {
            this.caldroidGridAdapters = caldroidGridAdapters;
        }

        /**
         * Return virtual next position
         *
         * @param position
         * @return
         */
        private int getNext(int position) {
            return (position + 1) % CalendarFragment.NUMBER_OF_PAGES;
        }

        /**
         * Return virtual previous position
         *
         * @param position
         * @return
         */
        private int getPrevious(int position) {
            return (position + 3) % CalendarFragment.NUMBER_OF_PAGES;
        }

        /**
         * Return virtual current position
         *
         * @param position
         * @return
         */
        public int getCurrent(int position) {
            return position % CalendarFragment.NUMBER_OF_PAGES;
        }

        @Override
        public void onPageScrollStateChanged(int position) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void refreshAdapters(int position) {
            // Get adapters to refresh
            CaldroidGridAdapter currentAdapter = caldroidGridAdapters
                    .get(getCurrent(position));
            CaldroidGridAdapter prevAdapter = caldroidGridAdapters
                    .get(getPrevious(position));
            CaldroidGridAdapter nextAdapter = caldroidGridAdapters
                    .get(getNext(position));

            if (position == currentPage) {
                // Refresh current adapter

                currentAdapter.setAdapterDateTime(currentDateTime);
                currentAdapter.notifyDataSetChanged();

                // Refresh previous adapter
                prevAdapter.setAdapterDateTime(currentDateTime.minus(0, 1, 0,
                        0, 0, 0, 0, DateTime.DayOverflow.LastDay));
                prevAdapter.notifyDataSetChanged();

                // Refresh next adapter
                nextAdapter.setAdapterDateTime(currentDateTime.plus(0, 1, 0, 0,
                        0, 0, 0, DateTime.DayOverflow.LastDay));
                nextAdapter.notifyDataSetChanged();
            }
            // Detect if swipe right or swipe left
            // Swipe right
            else if (position > currentPage) {
                // Update current date time to next month
                currentDateTime = currentDateTime.plus(0, 1, 0, 0, 0, 0, 0,
                        DateTime.DayOverflow.LastDay);

                // Refresh the adapter of next gridview
                nextAdapter.setAdapterDateTime(currentDateTime.plus(0, 1, 0, 0,
                        0, 0, 0, DateTime.DayOverflow.LastDay));
                nextAdapter.notifyDataSetChanged();

            }
            // Swipe left
            else {
                // Update current date time to previous month
                currentDateTime = currentDateTime.minus(0, 1, 0, 0, 0, 0, 0,
                        DateTime.DayOverflow.LastDay);

                // Refresh the adapter of previous gridview
                prevAdapter.setAdapterDateTime(currentDateTime.minus(0, 1, 0,
                        0, 0, 0, 0, DateTime.DayOverflow.LastDay));
                prevAdapter.notifyDataSetChanged();
            }

            // Update current page
            currentPage = position;
        }

        /**
         * Refresh the fragments
         */
        @Override
        public void onPageSelected(int position) {
            refreshAdapters(position);

            // Update current date time of the selected page
            setCalendarDateTime(currentDateTime);

            // Update all the dates inside current month
            CaldroidGridAdapter currentAdapter = caldroidGridAdapters
                    .get(position % CalendarFragment.NUMBER_OF_PAGES);

            // Refresh dateInMonthsList
            dateInMonthsList.clear();
            dateInMonthsList.addAll(currentAdapter.getDatetimeList());
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();

        try {
            Field childFragmentManager = Fragment.class
                    .getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
