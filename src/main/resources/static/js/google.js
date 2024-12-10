const CLIENT_ID = '402761261358-8j4oatc61kdgn23bbuk0mqk8s7hgoco7.apps.googleusercontent.com';
const API_KEY = 'AIzaSyDTFVY_ElsUuIPbk8LH79BdNvp4XFdQB2g';

let tokenClient;
let gapiInitialized = false;
let gisInitialized = false;

async function initializeGoogleApi() {
    try {
        await new Promise((resolve) => gapi.load('client', resolve));
        await gapi.client.init({
            apiKey: API_KEY,
            discoveryDocs: ['https://www.googleapis.com/discovery/v1/apis/calendar/v3/rest']
        });

        tokenClient = google.accounts.oauth2.initTokenClient({
            client_id: CLIENT_ID,
            scope: 'https://www.googleapis.com/auth/calendar.events',
        });

        gapiInitialized = true;
        gisInitialized = true;
        console.log('Google APIs initialized successfully');
    } catch (error) {
        console.error('Error initializing Google APIs:', error);
    }
}

window.addEventListener('load', initializeGoogleApi);

async function addToGoogleCalendar() {
    const btn = document.getElementById('calendarButton');
    btn.disabled = true;
    btn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> 處理中...';

    try {
        if (!gapiInitialized || !gisInitialized) {
            throw new Error('API 尚未初始化完成');
        }

        const eventName = document.getElementById('eventName').value;
        const eventDate = document.getElementById('eventDate').value;
        const orderId = document.getElementById('orderId').value;

        const startTime = new Date(eventDate);
        const endTime = new Date(startTime.getTime() + (2 * 60 * 60 * 1000));

        const event = {
            'summary': eventName,
            'description': '活動訂單編號: ' + orderId,
            'start': {
                'dateTime': startTime.toISOString(),
                'timeZone': 'Asia/Taipei'
            },
            'end': {
                'dateTime': endTime.toISOString(),
                'timeZone': 'Asia/Taipei'
            },
            'reminders': {
                'useDefault': false,
                'overrides': [
                    {'method': 'popup', 'minutes': 30}
                ]
            }
        };

        const response = await new Promise((resolve, reject) => {
            tokenClient.callback = async (resp) => {
                if (resp.error !== undefined) {
                    reject(resp);
                    return;
                }
                try {
                    const calendarResponse = await gapi.client.calendar.events.insert({
                        'calendarId': 'primary',
                        'resource': event
                    });
                    resolve(calendarResponse);
                } catch (err) {
                    reject(err);
                }
            };
            tokenClient.requestAccessToken();
        });

        // 使用回傳的 htmlLink 直接跳轉
        if (response && response.result && response.result.htmlLink) {
            alert('活動已成功加入 Google 日曆！即將前往查看。');
            window.open(response.result.htmlLink);
        } else {
            throw new Error('未能獲取行事曆連結');
        }

    } catch (error) {
        console.error('Error:', error);
        alert('添加失敗：' + (error.message || '請稍後再試'));
    } finally {
        btn.disabled = false;
        btn.innerHTML = '<i class="fas fa-calendar-plus"></i> 加入Google日曆';
    }
}